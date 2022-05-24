package pl.carsrental.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.carsrental.branch.Branch;
import pl.carsrental.branch.BranchService;
import pl.carsrental.cars.Car;
import pl.carsrental.cars.CarService;
import pl.carsrental.cars.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
//@RequestMapping(path = "/reservation")
@RequestMapping
public class ReservationController {

    private final ReservationService reservationService;

    private final CarService carService;

    private final BranchService branchService;

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.getReservations();
    }

    @GetMapping(path = "/auta/{carId}/create")
    public String showCreateReservationForm(@PathVariable("carId") Long carId, ModelMap modelMap) {
        modelMap.addAttribute("emptyReservation", new Reservation());
        modelMap.addAttribute("car", carService.getCar(carId));
        modelMap.addAttribute("branches", branchService.getBranches());
        modelMap.addAttribute("branchEnd", null);
        return "reservation-create";
    }

    @PostMapping(path = "/reservation/save")
    public String handleNewBook(
            @ModelAttribute("emptyReservation") Reservation reservation,
            @ModelAttribute("carId") Long carId,
            @ModelAttribute("branchEnd") Long branchId
    ){
        Car car = carService.getCar(carId);
        Branch branchEnd = branchService.getBranchById(branchId);
        BigDecimal price = reservationService.calcBookingPrice(reservation, car);

        switch (car.getStatus()) {
            case AVAILABLE:
                reservation.setReservationDate(LocalDateTime.now());
                reservation.setCarOnReservation(car);
                reservation.setPrice(price);
                reservation.setBranchStart(car.getBranch());
                car.setReservation(reservation);
                car.setStatus(Status.BORROWED);
                car.setBranch(branchEnd);
                reservationService.addReservation(reservation);
                log.info("Handle new Reservation: " + reservation);
                return "redirect:/branches";
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

    @DeleteMapping(path = "{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
    }
}
