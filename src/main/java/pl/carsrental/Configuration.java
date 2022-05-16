package pl.carsrental;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import pl.carsrental.branch.Branch;
import pl.carsrental.branch.BranchRepository;
import pl.carsrental.cars.Car;
import pl.carsrental.cars.CarRepository;
import pl.carsrental.cars.Status;
import pl.carsrental.client.Client;
import pl.carsrental.client.ClientRepository;
import pl.carsrental.employee.Employee;
import pl.carsrental.employee.EmployeeRepository;
import pl.carsrental.rental.RentRepository;
import pl.carsrental.rental.Rental;

import javax.transaction.Transactional;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(RentRepository rentRepository,
                                        BranchRepository branchRepository,
                                        EmployeeRepository employeeRepository,
                                        ClientRepository clientRepository,
                                        CarRepository carRepository) {
        return args -> {
            Client client1 = Client.builder()
                    .firstName("Mariusz")
                    .surname("Kowalczyk")
                    .adress("Krakow")
                    .email("m.kowalczyk@gmail.com")
                    .build();

            Car car1 = Car.builder()
                    .make("Audi")
                    .model("A8")
                    .mileage(182.023)
                    .status(Status.AVAILABLE)
                    .build();

            Car car2 = Car.builder()
                    .make("BMW")
                    .model("335i")
                    .mileage(123.721)
                    .firstRegistration(2008)
                    .pricePerDay(450)
                    .status(Status.UNAVAILABLE)
                    .build();

            Employee employee1 = Employee.builder()
                    .firstName("Marcin")
                    .surname("Nowacki")
//                    .standing(Stand.EMPLOYEER)
                    .build();

            Employee employee2 = Employee.builder()
                    .firstName("Jagoda")
                    .surname("Nowacka")
//                    .standing(Stand.EMPLOYEER)
                    .build();

            Branch branchWwa = Branch.builder()
                    .adress("Warszawa")
                    .build();

            Branch branchGd = Branch.builder()
                    .adress("Gdansk")
                    .build();

            branchRepository.saveAll(
                    List.of(
                            branchWwa,
                            branchGd
                    )
            );


            Rental rental2 = Rental.builder()
                    .email("wypo2@gmail.com")
                    .name("Wypozyczalnia aut2")
                    .owner("Jan Kowalski")
                    .webDomain("www.wypo2.com")
                    .branchList(
                            List.of(branchGd)
                    )
                    .build();

            Rental rental1 = Rental.builder()
                    .email("wypo@gmail.com")
                    .name("Wypozyczalnia aut")
                    .owner("Jan nowak")
                    .webDomain("www.wypo1.com")
                    .branchList(List.of(
                            branchWwa
                    ))
                    .build();

            rentRepository.saveAll(
                    List.of(
                            rental1,
                            rental2
                    )
            );


            employeeRepository.saveAll(
                    List.of(
                            employee1,
                            employee2
                    )
            );

            clientRepository.saveAll(
                    List.of(
                            client1
                    )
            );

            carRepository.saveAll(
                    List.of(
                            car1,
                            car2
                    )
            );
        };

    }
}
