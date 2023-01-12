package pl.carsrental.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.carsrental.branch.Branch;
import pl.carsrental.branch.BranchService;
import pl.carsrental.cars.Car;
import pl.carsrental.cars.CarService;
import pl.carsrental.cars.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping
public class ReservationController {

    private final ReservationService reservationService;

    private final CarService carService;

    private final BranchService branchService;

    @GetMapping(path = "/admin/reservations")
    public String getReservations(ModelMap modelMap) {
        modelMap.addAttribute("reservations", reservationService.getReservations());
        return "reservation-panel";
    }

    @GetMapping(path = "/reservations")
    public String getPhoneNumberToReservation(ModelMap modelMap) {
        modelMap.addAttribute("emptyPhoneNumber", null);
        return "reservation-tel";
    }

    @PostMapping(path = "/reservation/get")
    public String handlePhoneNumber(@RequestParam String emptyPhoneNumber) { //@ModelAttribute("emptyPhoneNumber")
        return "redirect:/reservations/" + emptyPhoneNumber;
    }

    @GetMapping(path = "/reservations/{phoneNumber}")
    public String getReservationsByTel(@PathVariable("phoneNumber") String phoneNumber, ModelMap modelMap) {
        modelMap.addAttribute("reservations", reservationService.getReservationsByPhoneNumber(phoneNumber));
        modelMap.addAttribute("phoneNumber", phoneNumber);
        return "reservations";
    }

    @PostMapping(path = "/reservation/save")
    public String handleNewReservation(
            @ModelAttribute("emptyReservation") Reservation reservation,
            @ModelAttribute("carId") Long carId,
            @ModelAttribute("branchEnd") Long endBranchId
    ){
        Car car = carService.getCarById(carId);

        return statusValidatorRedirect(reservation, endBranchId, car);
    }

    @GetMapping(path = "/cars/{carId}/create")
    public String showCreateReservationForm(@PathVariable("carId") Long carId, ModelMap modelMap) {
        modelMap.addAttribute("emptyReservation", new Reservation());
        modelMap.addAttribute("car", carService.getCarById(carId));
        modelMap.addAttribute("branches", branchService.getBranches());
        modelMap.addAttribute("branchStart", null);
        modelMap.addAttribute("branchEnd", null);
        return "reservation-create";
    }

    @GetMapping(path = "/admin/reservation/delete/{reservationId}")
    public String deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
        log.info("Deleted reservation with id " + reservationId);
        return "redirect:/admin/reservations";
    }

    @GetMapping(path = "/reservation/thanks")
    public String reservationThanksTemplate() {
        return "reservation-thanks";
    }

    private String statusValidatorRedirect(Reservation reservation, Long endBranchId, Car car) {
        switch (car.getStatus()) {
            case AVAILABLE:
                return dateValidatorRedirect(reservation, endBranchId, car);
            case BORROWED:
                log.error("Car is borrowed");
                return "redirect:/error/car-status";
            case UNAVAILABLE:
                log.error("Car is currently unavailable");
                return "redirect:/error/car-status";
            default:
                log.error("Wrong car status");
                return "redirect:/error/car-status";
        }
    }

    private String dateValidatorRedirect(Reservation reservation, Long endBranchId, Car car) {
        if (reservationService.isDateCorrect(reservation)) {
            addReservation(reservation, endBranchId, car);
            log.info("Handle new Reservation: " + reservation);
            return "redirect:/reservation/thanks";
        } else {
            log.error("Wrong date");
            return "redirect:/error/reservation-date";
        }
    }

    private void addReservation(Reservation reservation, Long endBranchId, Car car) {
        Branch currentCarBranch = car.getBranch();
        Branch branchEnd = branchService.getBranchById(endBranchId);
        BigDecimal price = reservationService.calcBookingPrice(reservation, car);

        updateReservationInfo(reservation, car, price);

        updateCarInfo(reservation, car, currentCarBranch, branchEnd);

        reservationService.addReservation(reservation);
    }

    private void updateReservationInfo(Reservation reservation, Car car, BigDecimal price) {
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setCarOnReservation(car);
        reservation.setPrice(price);
    }

    private void updateCarInfo(Reservation reservation, Car car, Branch currentCarBranch, Branch branchEnd) {
        car.setReservation(reservation);
        car.setStatus(Status.BORROWED);
        car.setPreviousBranch(currentCarBranch);
        car.setBranch(branchEnd);
    }
}
