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
import pl.carsrental.client.ClientService;

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

    private final ClientService clientService;

    @GetMapping(path = "/admin/reservations")
    public String getReservations(ModelMap modelMap) {
        modelMap.addAttribute("reservations", reservationService.getReservations());
        return "reservation-panel";
    }

    @GetMapping(path = "/reservations")
    public String getPhoneNumberToReservation(ModelMap modelMap) {
        modelMap.addAttribute("emptyPhoneNumber", null);
        return "reservation-tel";
    }

    @PostMapping(path = "/reservation/get")
    public String handlePhoneNumber(@RequestParam String emptyPhoneNumber) { //@ModelAttribute("emptyPhoneNumber")
        return "redirect:/reservations/" + emptyPhoneNumber;
    }

    @GetMapping(path = "/reservations/{phoneNumber}")
    public String getReservationsByTel(@PathVariable("phoneNumber") String phoneNumber, ModelMap modelMap) {
        modelMap.addAttribute("reservations", reservationService.getReservationsByPhoneNumber(phoneNumber));
        modelMap.addAttribute("phoneNumber", phoneNumber);
        return "reservations";
    }

    @GetMapping(path = "/cars/{carId}/create")
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
                    return "redirect:/reservation/thanks";
                } else {
                    log.error("Wrong date");
                    return "redirect:/error/reservation-date";
                }
            case BORROWED:
                log.error("Car is borrowed");
                return "redirect:/error/car-status";
            case UNAVAILABLE:
                log.error("Car is currently unavailable");
                return "redirect:/error/car-status";
            default:
                log.error("Wrong car status");
                return "redirect:/error/car-status";
        }
    }

    @GetMapping(path = "/admin/reservation/delete/{reservationId}")
    public String deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
        log.info("Deleted reservation with id " + reservationId);
        return "redirect:/admin/reservations";
    }

    @GetMapping(path = "/reservation/thanks")
    public String reservationThanksTemplate() {
        return "reservation-thanks";
    }
}
