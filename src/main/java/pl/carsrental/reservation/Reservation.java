package pl.carsrental.reservation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.carsrental.branch.Branch;
import pl.carsrental.cars.Car;
import pl.carsrental.client.Client;
import pl.carsrental.hire.Hire;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private LocalDateTime reservationDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Client client;

//    @OneToOne(mappedBy = "reservation")
    @OneToOne
    private Car carOnReservation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    @ManyToOne
    private Branch branchStart;

    @ManyToOne
    private Branch branchEnd;

    private BigDecimal price;

    @OneToOne
    private Hire hire;

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Reservation() {
    }

}
