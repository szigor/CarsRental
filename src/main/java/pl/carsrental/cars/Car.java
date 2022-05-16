package pl.carsrental.cars;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.carsrental.branch.Branch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotBlank

    private String make;
//    @NotBlank

    private String model;

    //    @NotBlank
    private BodyType bodyType;

    //    @NotBlank
    private int firstRegistration;

    //    @NotBlank
    private Colour colour;

    //    @NotBlank
    private double mileage;

    //    @NotBlank
    @Enumerated(EnumType.STRING)
    private Status status;

    //    @NotBlank
    private double pricePerDay;
    @ManyToOne
    private Branch branch;

    @SuppressWarnings(value = "unused")
    protected Car() {
    }
}
