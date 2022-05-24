package pl.carsrental.cars;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
//@RequestMapping(path = "/auta")
@RequiredArgsConstructor
@Transactional
public class CarController {

    private final CarService carService;

    @GetMapping(path = "/admin/cars")
    public String carsManagement(ModelMap modelMap) {
        modelMap.addAttribute("cars", carService.getCars());
        return "car-panel";
    }

    @GetMapping("/auta")
    public String getCars(ModelMap modelMap) {
        modelMap.addAttribute("cars", carService.getCars());
        return "home";
    }

    @GetMapping (path = "/auta/{carId}")
    public String getCar(@PathVariable("carId") Long carId, ModelMap modelMap) {
        modelMap.addAttribute("car", carService.getCar(carId));
        return "car-details";
    }

    @PostMapping(path = "/auta/add")
    public void addCar(@RequestBody Car car) {
        carService.addCar(car);
    }

    @DeleteMapping(path = "/auta/delete/{carId}")
    public void deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCarById(carId);
    }
}

