package pl.carsrental.branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.carsrental.cars.Car;
import pl.carsrental.employee.Employee;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Branch {
    @Id
    private long id;
    @NotBlank
    private String adress;
    private List<Employee> employeeList;
    private List<Car> carsList;
    @NotBlank
    private Employee manager;


    @Override
    public String toString() {
        return "Branch{" +
                "adress='" + adress + '\'' +
                ", employeeList=" + employeeList +
                ", carsList=" + carsList +
                ", manager=" + manager +
                '}';
    }
}
