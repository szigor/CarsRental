package pl.carsrental;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.carsrental.branch.Branch;
import pl.carsrental.branch.BranchRepository;
import pl.carsrental.cars.*;
import pl.carsrental.client.Client;
import pl.carsrental.client.ClientRepository;
import pl.carsrental.employee.Employee;
import pl.carsrental.employee.EmployeeRepository;
import pl.carsrental.employee.Stand;
import pl.carsrental.rental.RentRepository;
import pl.carsrental.rental.Rental;
import pl.carsrental.reservation.Reservation;
import pl.carsrental.reservation.ReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Component
@Transactional
@RequiredArgsConstructor
public class InitialDataSeeder implements Runnable{

    private final RentRepository rentRepository;
    private final BranchRepository branchRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void run() {
        Client client1 = clientRepository.save(Client.builder()
                .firstName("Mariusz")
                .surname("Kowalczyk")
                .adress("Krakow")
                .email("m.kowalczyk@gmail.com")
                .build());


        Car car1 = carRepository.save(Car.builder()
                .make("Audi")
                .model("A8")
                .horsePower(350)
                .capacity(4.134)
                .firstRegistration(2012)
                .mileage(182.023)
                .pricePerDay(700.0)
                .bodyType(BodyType.SALOON)
                .fuel(Fuel.DIESEL)
                .colour(Colour.BLACK)
                .status(Status.AVAILABLE)
                .build());

        Car car2 = carRepository.save(Car.builder()
                .make("BMW")
                .model("335i")
                .horsePower(306)
                .capacity(2.979)
                .firstRegistration(2008)
                .mileage(123.721)
                .pricePerDay(800.0)
                .bodyType(BodyType.COUPE)
                .fuel(Fuel.PETROL)
                .colour(Colour.WHITE)
                .status(Status.UNAVAILABLE)
                .build());

        Car car3 = carRepository.save(Car.builder()
                .make("Mercedes-benz")
                .model("G63")
                .horsePower(563)
                .capacity(5.461)
                .firstRegistration(2016)
                .mileage(47.443)
                .pricePerDay(2000.0)
                .bodyType(BodyType.SUV)
                .fuel(Fuel.PETROL)
                .colour(Colour.SILVER)
                .status(Status.BORROWED)
                .build());

        Car car4 = carRepository.save(Car.builder()
                .make("Nissan")
                .model("GT-R")
                .horsePower(565)
                .capacity(3.799)
                .firstRegistration(2019)
                .mileage(19.885)
                .pricePerDay(1800.00)
                .bodyType(BodyType.COUPE)
                .fuel(Fuel.PETROL)
                .colour(Colour.BLUE)
                .status(Status.AVAILABLE)
                .build());

        Car car5 = carRepository.save(Car.builder()
                .make("Tesla")
                .model("Model 3")
                .horsePower(497)
                .firstRegistration(2021)
                .mileage(9.332)
                .pricePerDay(1500.00)
                .bodyType(BodyType.SALOON)
                .fuel(Fuel.ELECTRIC)
                .colour(Colour.RED)
                .status(Status.AVAILABLE)
                .build());


        Employee employee1 = employeeRepository.save(Employee.builder()
                .firstName("Marcin")
                .surname("Nowacki")
                .standing(Stand.EMPLOYEE)
                .build());

        Employee employee2 = employeeRepository.save(Employee.builder()
                .firstName("Jagoda")
                .surname("Nowacka")
                .standing(Stand.EMPLOYEE)
                .build());

        Employee manager = employeeRepository.save(Employee.builder()
                .firstName("Mariusz")
                .surname("Pudzianowski")
                .standing(Stand.MANAGER)
                .build());


        Branch branchWwa = branchRepository.save(Branch.builder()
                .address("Warszawa")
                .cars(List.of(car1,car2))
                .employees(List.of(employee1,employee2))
                .build());

        Branch branchGd = branchRepository.save(Branch.builder()
                .address("Gdansk")
                .employee(manager)
                .build());

        Branch branchWro = branchRepository.save(Branch.builder()
                .address("Wroclaw")
                .build());


        Rental rental1 = rentRepository.save(Rental.builder()
                .email("wypo@gmail.com")
                .name("Wypozyczalnia aut")
                .owner("Jan nowak")
                .webDomain("www.wypo1.com")
                .branches(
                        List.of(
                                branchWwa,
                                branchWro
                        )
                )
                .build());

        Rental rental2 = rentRepository.save(Rental.builder()
                .email("wypo2@gmail.com")
                .name("Wypozyczalnia aut2")
                .owner("Jan Kowalski")
                .webDomain("www.wypo2.com")
                .branches(
                        List.of(
                                branchGd
                        )
                )
                .build());


        Reservation reservation1 = reservationRepository.save(Reservation.builder()
                .branchStart(branchGd)
                .branchEnd(branchWwa)
                .carsOnReservation(List.of(car1))
                .client(client1)
                .reservationDate(LocalDateTime.now())
                .price(BigDecimal.valueOf(349.99))
                .fromDate(LocalDateTime.now().plusDays(1))
                .toDate(LocalDateTime.now().plusDays(6))
                .build());
    }
}
