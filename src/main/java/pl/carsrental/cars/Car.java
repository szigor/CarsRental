package pl.carsrental.cars;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.carsrental.branch.Branch;
import pl.carsrental.reservation.Reservation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Car {

    @Column(name = "fee")
    private final BigDecimal otherPickUpLocationFee = BigDecimal.valueOf(80.0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String make;

    @NotEmpty
    private String model;

    private Integer horsePower;

    private Double capacity;

    @NotNull
    private Integer firstRegistration;

    @NotNull
    private Double mileage;

    @NotNull
    private Double pricePerDay;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Branch previousBranch;

    @OneToOne(mappedBy = "carOnReservation")
//    @OneToOne
    private Reservation reservation;

    @SuppressWarnings(value = "unused")
    protected Car() {
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", status=" + status +
                '}';
    }
}
