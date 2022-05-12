package pl.carsrental.branch;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.carsrental.cars.Car;
import pl.carsrental.employee.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String adress;
    @OneToMany(mappedBy = "branch")
    private List<Employee> employeeList;
    @OneToMany
    private List<Car> carsList;
//    @NotBlank
    @OneToOne
    private Employee manager;

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Branch() {
    }
}
