package pl.carsrental.cars;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@RequestMapping(path = "/auta")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping(path = "/auta")
    public String getCars(ModelMap modelMap) {
        modelMap.addAttribute("cars", carService.getCars());
        return "home";
    }

    @GetMapping(path = "/autaa")
    public List<Car> getCarss() {
        return carService.getCars();
    }

    @GetMapping (path = "/auta/{carId}")
    public String getCar(@PathVariable("carId") Long carId, ModelMap modelMap) {
        modelMap.addAttribute("car", carService.getCar(carId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()));
        return "car-details";
    }

    @PostMapping
    public void addCar(@RequestBody Car car) {
        carService.addCar(car);
    }

    @DeleteMapping(path = "delete/{carId}")
    public void deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCarById(carId);
    }
}

