package pl.carsrental.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        return carRepository.findAll();
    }

    public Optional<Car> getCar(long carID) {
        try {
            return carRepository.findById(carID);
        } catch(EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public void addCar(Car car) {
        carRepository.save(car);
        log.info("Added " + car);
    }

    public void deleteCarById(Long carId) {
        isCarExist(carId);
        carRepository.deleteById(carId);
    }

    private void isCarExist(Long carId) {
        boolean exists = carRepository.existsById(carId);
        if (!exists) {
            throw new IllegalStateException("car with id " + carId + " does not exists");
        }
    }
}
