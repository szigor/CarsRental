package pl.carsrental.cars;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.carsrental.branch.Branch;
import pl.carsrental.branch.BranchService;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class CarController {

    private final CarService carService;
    private final BranchService branchService;

    @GetMapping("/cars")
    public String getCars(ModelMap modelMap) {
        modelMap.addAttribute("cars", carService.getCars());
        return "home";
    }

    @GetMapping (path = "/cars/{carId}")
    public String getCar(@PathVariable("carId") Long carId, ModelMap modelMap) {
        modelMap.addAttribute("car", carService.getCarById(carId));
        modelMap.addAttribute("statusAvailable", Status.AVAILABLE);
        modelMap.addAttribute("statusBorrowed", Status.BORROWED);
        modelMap.addAttribute("statusUnavailable", Status.UNAVAILABLE);
        modelMap.addAttribute("fee", carService.getCarById(carId).getOtherPickUpLocationFee());
        return "car-details";
    }

    @GetMapping(path = "/admin/cars")
    public String carsManagement(ModelMap modelMap) {
        modelMap.addAttribute("cars", carService.getCars());
        return "car-panel";
    }

    @GetMapping(path = "/admin/car/create")
    public String showCreateCarForm(ModelMap modelMap) {
        modelMap.addAttribute("emptyCar", new Car());
        modelMap.addAttribute("statusValues", Status.values());
        modelMap.addAttribute("fuelValues", Fuel.values());
        modelMap.addAttribute("bodyTypeValues", BodyType.values());
        modelMap.addAttribute("branches", branchService.getBranches());
        return "car-create";
    }

    @PostMapping(path = "/admin/car/save")
    public String handleNewCar(@ModelAttribute("emptyCar") Car car) {
        log.info("Handle new car: " + car);
        carService.save(car);
        return "redirect:/admin/cars";
    }

    @PostMapping(path = "/admin/car/update")
    public String handleUpdatedCar(@ModelAttribute("car") Car car) {
        log.info("Handle updated car: " + car);
        carService.save(car);
        return "redirect:/admin/cars";
    }

    @GetMapping(path = "/admin/car/edit/{carId}")
    public String showUpdateCarForm(@PathVariable Long carId, ModelMap modelMap) {
        modelMap.addAttribute("car", carService.getCarById(carId));
        modelMap.addAttribute("statusValues", Status.values());
        modelMap.addAttribute("fuelValues", Fuel.values());
        modelMap.addAttribute("bodyTypeValues", BodyType.values());
        modelMap.addAttribute("branches", branchService.getBranches());
        return "car-edit";
    }

    @GetMapping(path = "/admin/car/delete/{carId}")
    public String deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCarById(carId);
        log.info("Deleted car with id " + carId);
        return "redirect:/admin/cars";
    }
}

