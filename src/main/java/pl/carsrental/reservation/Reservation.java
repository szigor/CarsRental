package pl.carsrental.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.carsrental.branch.Branch;
import pl.carsrental.cars.Car;
import pl.carsrental.client.Client;
import pl.carsrental.hire.Hire;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Reservation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private LocalDateTime reservationDate;

    @OneToOne
    private Client client;

//    @OneToMany(mappedBy = "reservation")
    @OneToMany
    private List<Car> carsOnReservation;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    @ManyToOne
    private Branch branchStart;

    @ManyToOne
    private Branch branchEnd;

    private BigDecimal price;

    @OneToOne
    private Hire hire;

}
