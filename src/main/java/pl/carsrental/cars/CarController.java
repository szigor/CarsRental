package pl.carsrental.cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping(path = "/auta")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getCars() {
        return carService.getCars();
    }


    @GetMapping (path = "status/{carId}")
    public ResponseEntity<Car> getCar(@PathVariable("carId") Long carId) {
        return carService.getCar(carId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public void addCar(@RequestBody Car car) {
        carService.addCar(car);
    }

    @DeleteMapping(path = "{carId}")
    public void deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCarById(carId);
    }
}

