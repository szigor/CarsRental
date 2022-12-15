package pl.carsrental.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.carsrental.reservation.Reservation;

@Controller
@RequestMapping(path = "/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping(path = "/create")
    public String register(@ModelAttribute("request") RegistrationRequest request) {
        registrationService.register(request);
        return "redirect:/cars";
    }

    @GetMapping
    public String showCreateReservationForm(ModelMap modelMap) {
        modelMap.addAttribute("emptyRequest", new RegistrationRequest());
        return "register";
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
