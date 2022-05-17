package pl.carsrental.branch;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import pl.carsrental.cars.Car;
import pl.carsrental.employee.Employee;
import pl.carsrental.employee.Stand;
import pl.carsrental.rental.Rental;

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

//    // oddzial z ktorego auto wyruszylo
//    @OneToMany(mappedBy = "branchStart")
//    private List<Reservation> reservationStart;
//
//    // oddzial w ktorym auto zostalo oddane
//    @OneToMany(mappedBy = "branchEnd")
//    private List<Reservation> reservationEnd;

    @ManyToOne
    private Rental rental;

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Branch() {
    }

    @Builder
    private Branch(Long id, @NotBlank String address, @Singular List<Employee> employees, @Singular List<Car> cars, Rental rental) {
        this.id = id;
        this.address = address;
        this.employees = employees;
        this.cars = cars;
        this.rental = rental;
        employees.forEach(employee -> {employee.setBranch(this);});
        cars.forEach(car -> {car.setBranch(this);});
    }
}
