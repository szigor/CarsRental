package pl.carsrental.hire;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.carsrental.employee.Employee;
import pl.carsrental.reservation.Reservation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Hire {

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Hire() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Employee employee;

    private LocalDateTime hireDate;

    @OneToOne
    private Reservation reservation;

    private String comments;

}
