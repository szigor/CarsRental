package pl.carsrental.branch;

import lombok.Data;
import pl.carsrental.cars.Car;
import pl.carsrental.employee.Employee;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
public class Branch {
    @Id
    private Long id;
    @NotBlank
    private String adress;
    @OneToMany(mappedBy = "branch")
    private List<Employee> employeeList;
    @OneToMany
    private List<Car> carsList;
    @NotBlank
    @OneToOne
    private Employee manager;

}
