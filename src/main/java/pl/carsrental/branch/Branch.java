package pl.carsrental.branch;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.carsrental.cars.Car;
import pl.carsrental.employee.Employee;
import pl.carsrental.rental.Rental;
import pl.carsrental.reservation.Reservation;

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
    private String address;

    @OneToMany(mappedBy = "branch")
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "branch")
    private List<Car> carsList;

    //    @NotBlank
    @OneToOne
    private Employee manager;

    // oddzial z ktorego auto wyruszylo
    @OneToMany(mappedBy = "branchStart")
    private List<Reservation> reservationStart;

    // oddzial w ktorym auto zostalo oddane
    @OneToMany(mappedBy = "branchEnd")
    private List<Reservation> reservationEnd;

    @ManyToOne
    private Rental rental;

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Branch() {
    }
}
