package pl.carsrental.cars;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(path = "/auta")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public String getCars(ModelMap modelMap) {
        modelMap.addAttribute("cars", carService.getCars());
        return "home";
    }

    @GetMapping (path = "/{carId}")
    public String getCar(@PathVariable("carId") Long carId, ModelMap modelMap) {
        modelMap.addAttribute("car", carService.getCar(carId));
        return "car-details";
    }

    @PostMapping(path = "/add")
    public void addCar(@RequestBody Car car) {
        carService.addCar(car);
    }

    @DeleteMapping(path = "delete/{carId}")
    public void deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCarById(carId);
    }
}

