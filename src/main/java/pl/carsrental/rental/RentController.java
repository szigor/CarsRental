package pl.carsrental.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/wypo")
public class RentController {

    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    public List<Rental> getRentals() {
        return rentService.getRentals();
    }

    @DeleteMapping(path = "{rentId}")
    public void deleteRent(@PathVariable("rentId") Long rentId) {
        rentService.deleteRent(rentId);
    }
}
