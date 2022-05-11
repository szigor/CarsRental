package pl.carsrental.rental;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RentConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(RentRepository rentRepository) {
        return args -> {
            Rental rental1 = Rental.builder()
                    .email("wypo@gmail.com")
                    .name("Wypozyczalnia aut")
                    .owner("Jan nowak")
                    .webDomain("www.wypo1.com")
                    .build();

            Rental rental2 = Rental.builder()
                    .email("wypo2@gmail.com")
                    .name("Wypozyczalnia aut2")
                    .owner("Jan Kowalski")
                    .webDomain("www.wypo2.com")
                    .build();

            rentRepository.saveAll(
                    List.of(rental1, rental2)
            );
        };


    }

}
