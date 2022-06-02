package pl.carsrental.cars;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getCars() {
        return carRepository.findAll(Sort.by("pricePerDay").ascending());
    }

    public Car getCarById(Long carId) {
        isCarExist(carId);
        return carRepository.getById(carId);
    }

    public void addCar(Car car) {
        carRepository.save(car);
        log.info("Added " + car);
    }

    public void deleteCarById(Long carId) {
        isCarExist(carId);
        carRepository.deleteById(carId);
    }

    public void save(Car car) {
        carRepository.save(car);
        log.info("Saved " + car);
    }

    private void isCarExist(Long carId) {
        boolean exists = carRepository.existsById(carId);
        if (!exists) {
            throw new IllegalStateException("car with id " + carId + " does not exists");
        }
    }
}
