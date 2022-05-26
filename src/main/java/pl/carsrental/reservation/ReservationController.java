package pl.carsrental.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.carsrental.branch.Branch;
import pl.carsrental.branch.BranchService;
import pl.carsrental.cars.Car;
import pl.carsrental.cars.CarService;
import pl.carsrental.cars.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping
public class ReservationController {

    private final ReservationService reservationService;

    private final CarService carService;

    private final BranchService branchService;

    @GetMapping(path = "/admin/reservations")
    public String getReservations(ModelMap modelMap) {
        modelMap.addAttribute("reservations", reservationService.getReservations());
        return "reservation-panel";
    }

    @GetMapping(path = "/auta/{carId}/create")
    public String showCreateReservationForm(@PathVariable("carId") Long carId, ModelMap modelMap) {
        modelMap.addAttribute("emptyReservation", new Reservation());
        modelMap.addAttribute("car", carService.getCarById(carId));
        modelMap.addAttribute("branches", branchService.getBranches());
        modelMap.addAttribute("branchEnd", null);
        return "reservation-create";
    }

    @PostMapping(path = "/reservation/save")
    public String handleNewReservation(
            @ModelAttribute("emptyReservation") Reservation reservation,
            @ModelAttribute("carId") Long carId,
            @ModelAttribute("branchEnd") Long branchId
    ){
        Car car = carService.getCarById(carId);
        Branch branchEnd = branchService.getBranchById(branchId);
        BigDecimal price = reservationService.calcBookingPrice(reservation, car);

        switch (car.getStatus()) {
            case AVAILABLE:
                if (reservationService.isDateCorrect(reservation)) {
                    reservation.setReservationDate(LocalDateTime.now());
                    reservation.setBranchStart(car.getBranch());
                    reservation.setCarOnReservation(car);
                    // if branch start is not equal to branch end - price += 200.00
                    reservation.setPrice(reservationService.isBranchEndSameToStart(reservation, price));
                    car.setReservation(reservation);
                    car.setStatus(Status.BORROWED);
                    car.setBranch(branchEnd);
                    reservationService.addReservation(reservation);
                    log.info("Handle new Reservation: " + reservation);
                    return "redirect:/branches";
                } else {
                    log.error("Wrong date");
                    return "redirect:/auta";
                }
            case BORROWED:
                log.error("Car is borrowed");
                return "redirect:/auta";
            case UNAVAILABLE:
                log.error("Car is currently unavailable");
                return "redirect:/auta";
            default:
                log.error("Wrong car status");
                return "redirect:/auta";
        }
    }

    @GetMapping(path = "/admin/reservation/delete/{reservationId}")
    public String deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
        log.info("Deleted reservation with id " + reservationId);
        return "redirect:/admin/reservations";
    }
}
