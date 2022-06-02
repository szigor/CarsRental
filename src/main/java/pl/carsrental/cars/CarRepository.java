package pl.carsrental.cars;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> getCars();

    Car getCarById(Long carId);

    void addCar(Car car);

    void deleteCarById(Long carId);
}
