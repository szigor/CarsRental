package pl.carsrental;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
public class Configuration {

    @Bean
    CommandLineRunner commandLineRunner(InitialDataSeeder initialDataSeeder) {
        return args -> { initialDataSeeder.run();
        };
    }
}
