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
            @ModelAttribute("branchEnd") String branch
    ){
        Car car = carService.getCar(carId);

        if (car.getStatus() == Status.AVAILABLE) {

            car.setReservation(reservation);
            car.setStatus(Status.BORROWED);
            reservation.setCarOnReservation(car);
            reservation.setReservationDate(LocalDateTime.now());
            reservationService.addReservation(reservation);
            log.info("Handle new Reservation: " + reservation);
            return "redirect:/branches";
        } else {
            switch (car.getStatus()) {
                case BORROWED:
                    log.error("Samochód wypozyczony");
                case UNAVAILABLE:
                    log.error("Samochód niedostępny");
            }
            return "redirect:/auta";
        }
    }

    @DeleteMapping(path = "{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
    }
}
