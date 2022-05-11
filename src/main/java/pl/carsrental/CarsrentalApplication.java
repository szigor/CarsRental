package pl.carsrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.carsrental.rental.Rental;

@SpringBootApplication
public class CarsrentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsrentalApplication.class, args);

//		List<Car> carList = List.of(new Car("BMW"));
//
//		List<Branch> listaOddzialow = List.of(new Branch("Wwa"), new Branch("Wroclaw"));
//
//		Branch branch = new Branch("Gdansk");
//
//		Rental rental1 = new Rental(
//				"Wypo1",
//				"www.carsrental.com",
//				"carrent@gmail.com",
//				"Jan Nowak",
//				"url",
//				listaOddzialow
//				);
//
//		System.out.println(rental1);
//		System.out.println(rental1.getBranchList());
//		rental1.addBranch(branch);
	}

}
