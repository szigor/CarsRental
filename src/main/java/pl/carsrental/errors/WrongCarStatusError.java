package pl.carsrental.errors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WrongCarStatusError {

    @GetMapping(path = "/error/car-status")
    public String wrongCarStatusErrorTemplate() {
        return "error-car-status";
    }
}
