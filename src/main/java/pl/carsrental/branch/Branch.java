package pl.carsrental.branch;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import pl.carsrental.cars.Car;
import pl.carsrental.employee.Employee;
import pl.carsrental.employee.Stand;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String address;

    @OneToMany(mappedBy = "branch")
    private List<Employee> employees;

    @OneToMany(mappedBy = "branch")
    private List<Car> cars;

    public Employee getManager() {
        return employees.stream().filter(employee -> employee.getStanding() == Stand.MANAGER).findFirst().orElse(null);
    }

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Branch() {
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", address='" + address + '\'' +
                '}';
    }

    @Builder
    private Branch(Long id, @NotBlank String address, @Singular List<Employee> employees, @Singular List<Car> cars) {
        this.id = id;
        this.address = address;
        this.employees = employees;
        this.cars = cars;
        employees.forEach(employee -> {employee.setBranch(this);});
        cars.forEach(car -> {car.setBranch(this);});
    }
}
