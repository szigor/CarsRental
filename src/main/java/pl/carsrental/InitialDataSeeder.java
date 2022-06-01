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
import pl.carsrental.reservation.ReservationRepository;

import java.util.List;


@Component
@Transactional
@RequiredArgsConstructor
public class InitialDataSeeder implements Runnable {

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


        Car a8 = carRepository.save(Car.builder()
                .make("Audi")
                .model("A8")
                .horsePower(435)
                .capacity(4.0)
                .firstRegistration(2018)
                .mileage(103.023)
                .pricePerDay(260.0)
                .bodyType(BodyType.SALOON)
                .fuel(Fuel.DIESEL)
                .status(Status.UNAVAILABLE)
                .build());

        Car m5 = carRepository.save(Car.builder()
                .make("BMW")
                .model("M5")
                .horsePower(600)
                .capacity(4.4)
                .firstRegistration(2019)
                .mileage(47.721)
                .pricePerDay(460.0)
                .bodyType(BodyType.SALOON)
                .fuel(Fuel.PETROL)
                .status(Status.BORROWED)
                .build());

        Car g63 = carRepository.save(Car.builder()
                .make("Mercedes-benz")
                .model("G63")
                .horsePower(585)
                .capacity(4.0)
                .firstRegistration(2020)
                .mileage(18.443)
                .pricePerDay(480.0)
                .bodyType(BodyType.SUV)
                .fuel(Fuel.PETROL)
                .status(Status.AVAILABLE)
                .build());

        Car gtr = carRepository.save(Car.builder()
                .make("Nissan")
                .model("GT-R")
                .horsePower(565)
                .capacity(3.8)
                .firstRegistration(2019)
                .mileage(19.885)
                .pricePerDay(450.0)
                .bodyType(BodyType.COUPE)
                .fuel(Fuel.PETROL)
                .status(Status.AVAILABLE)
                .build());

        Car ferrari812 = carRepository.save(Car.builder()
                .make("Ferrari")
                .model("812")
                .horsePower(800)
                .capacity(6.5)
                .firstRegistration(2022)
                .mileage(2.885)
                .pricePerDay(600.0)
                .bodyType(BodyType.COUPE)
                .fuel(Fuel.PETROL)
                .status(Status.AVAILABLE)
                .build());

        Car panamera = carRepository.save(Car.builder()
                .make("Porsche")
                .model("Panamera")
                .horsePower(422)
                .capacity(4.0)
                .firstRegistration(2017)
                .mileage(95.885)
                .pricePerDay(280.0)
                .bodyType(BodyType.SALOON)
                .fuel(Fuel.DIESEL)
                .status(Status.AVAILABLE)
                .build());

        Car arteon = carRepository.save(Car.builder()
                .make("Volkswagen")
                .model("Arteon")
                .horsePower(316)
                .capacity(2.0)
                .firstRegistration(2021)
                .mileage(19.885)
                .pricePerDay(200.0)
                .bodyType(BodyType.KOMBI)
                .fuel(Fuel.PETROL)
                .status(Status.AVAILABLE)
                .build());

        Car xf = carRepository.save(Car.builder()
                .make("Jaguar")
                .model("XF")
                .horsePower(295)
                .capacity(3.0)
                .firstRegistration(2016)
                .mileage(128.885)
                .pricePerDay(160.0)
                .bodyType(BodyType.SALOON)
                .fuel(Fuel.DIESEL)
                .status(Status.AVAILABLE)
                .build());

        Car modelS = carRepository.save(Car.builder()
                .make("Tesla")
                .model("Model S")
                .horsePower(422)
                .firstRegistration(2019)
                .mileage(33.332)
                .pricePerDay(240.0)
                .bodyType(BodyType.SALOON)
                .fuel(Fuel.ELECTRIC)
                .status(Status.AVAILABLE)
                .build());


        Employee employee1 = employeeRepository.save(Employee.builder()
                .firstName("Marcin")
                .surname("Nowacki")
                .standing(Stand.EMPLOYEE)
                .email("m.nowacki@wp.pl")
                .address("Gliwice")
                .build());

        Employee employee2 = employeeRepository.save(Employee.builder()
                .firstName("Jagoda")
                .surname("Krajniak")
                .standing(Stand.EMPLOYEE)
                .email("j.krajniak@wp.pl")
                .address("Radom")
                .build());

        Employee manager1 = employeeRepository.save(Employee.builder()
                .firstName("Nikodem")
                .surname("Gorecki")
                .standing(Stand.MANAGER)
                .email("m.pudzianowski@wp.pl")
                .address("Warszawa")
                .build());

        Employee manager2 = employeeRepository.save(Employee.builder()
                .firstName("Wiktor")
                .surname("Kędzierski")
                .standing(Stand.MANAGER)
                .email("m.pudzianowski@wp.pl")
                .address("Gdansk")
                .build());

        Employee manager3 = employeeRepository.save(Employee.builder()
                .firstName("Juliusz")
                .surname("Nowakowski")
                .standing(Stand.MANAGER)
                .email("m.pudzianowski@wp.pl")
                .address("Wroclaw")
                .build());


        Branch branchWwa = branchRepository.save(Branch.builder()
                .address("Warszawa")
                .cars(List.of(modelS, ferrari812, g63, a8))
                .employees(List.of(employee1, employee2, manager1))
                .build());

        Branch branchGd = branchRepository.save(Branch.builder()
                .address("Gdansk")
                .cars(List.of(arteon, m5, xf))
                .employee(manager2)
                .build());

        Branch branchWro = branchRepository.save(Branch.builder()
                .address("Wroclaw")
                .cars(List.of(gtr, panamera))
                .employee(manager3)
                .build());


        Rental rental1 = rentRepository.save(Rental.builder()
                .email("wypo@gmail.com")
                .name("Car Rental")
                .owner("Jan nowak")
                .webDomain("www.wypo1.com")
                .branches(
                        List.of(
                                branchWwa,
                                branchWro,
                                branchGd
                        )
                )
                .build());

//        Reservation reservation1 = reservationRepository.save(Reservation.builder()
//                .branchStart(branchGd)
//                .branchEnd(branchWwa)
//                .carOnReservation(car1)
//                .client(client1)
//                .reservationDate(LocalDateTime.now())
//                .price(BigDecimal.valueOf(349.99))
////                .fromDate(LocalDateTime.now().plusDays(1))
////                .toDate(LocalDateTime.now().plusDays(6))
//                .build());
    }
}
