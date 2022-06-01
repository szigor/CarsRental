package pl.carsrental.errors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WrongReservationDateError {

    @GetMapping(path = "/error/reservation-date")
    public String wrongReservationDateErrorTemplate() {
        return "error-reservation-date";
    }
}
