package pl.carsrental.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.getReservations();
    }

    @GetMapping(path = "/create")
    public String showCreateReservationForm(ModelMap modelMap) {
        modelMap.addAttribute("emptyReservation", new Reservation());
        return "reservation-create";
    }

    @PostMapping(path = "/save")
    public String handleNewBook(@ModelAttribute("emptyReservation") Reservation reservation) {
        log.info("Handle new Reservation: " + reservation);

        reservationService.addReservation(reservation);
        return "redirect:/branches";
    }

    @DeleteMapping(path = "{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
    }
}
