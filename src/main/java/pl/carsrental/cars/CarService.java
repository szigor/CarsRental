package pl.carsrental.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

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
    }

    public Boolean isAvailable(Long carId) {
        isCarExist(carId);
        Car car = carRepository.getById(carId);
        return car.getStatus() == Status.AVAILABLE;
    }

    private void isCarExist(Long carId) {
        boolean exists = carRepository.existsById(carId);
        if (!exists) {
            throw new IllegalStateException("car with id " + carId + " does not exists");
        }
    }
}
