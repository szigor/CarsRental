package pl.carsrental;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
public class Configuration {

    @Bean
    CommandLineRunner commandLineRunner(InitialDataSeeder initialDataSeeder) {
        return args -> { initialDataSeeder.run();
        };


    }

}
