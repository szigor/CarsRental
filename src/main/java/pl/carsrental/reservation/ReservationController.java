package pl.carsrental.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.carsrental.cars.Car;
import pl.carsrental.cars.CarService;
import pl.carsrental.cars.Status;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
//@RequestMapping(path = "/reservation")
@RequestMapping
public class ReservationController {

    private final ReservationService reservationService;

    private final CarService carService;

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.getReservations();
    }

    @GetMapping(path = "/auta/{carId}/create")
    public String showCreateReservationForm(@PathVariable("carId") Long carId, ModelMap modelMap) {
        modelMap.addAttribute("emptyReservation", new Reservation());
        modelMap.addAttribute("car", carService.getCar(carId));
        return "reservation-create";
    }

    @PostMapping(path = "/reservation/save")
    public String handleNewBook(
            @ModelAttribute("emptyReservation") Reservation reservation,
            @ModelAttribute("car") Car car){

        if (car.getStatus() == Status.AVAILABLE) {
            reservationService.addReservation(reservation);
            log.info("Handle new Reservation: " + reservation);
            return "redirect:/branches";
        } else {
            log.warn("Samochód niedostępny");
            return "redirect:/branches";
        }
    }

    @DeleteMapping(path = "{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
    }
}
