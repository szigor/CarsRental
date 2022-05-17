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
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String make;

    @NotBlank
    private String model;

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
    private Colour colour;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Reservation reservation;

    @SuppressWarnings(value = "unused")
    protected Car() {
    }
}
