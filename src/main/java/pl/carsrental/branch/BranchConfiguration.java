//package pl.carsrental.branch;

//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//@Configuration
//public class BranchConfiguration {
//
//    @Bean
//    @Transactional
//    CommandLineRunner commandLineRunner(BranchRepository branchRepository) {
//        return args -> {
//            Branch branchWwa = Branch.builder()
//                    .adress("Warszawa")
//                    .build();
//
//            branchRepository.saveAll(
//                    List.of(branchWwa)
//            );
//        };
//    }
//
//}
