package pl.carsrental.cars;

import lombok.Data;
import pl.carsrental.branch.Branch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String make;
    @NotBlank
    private String model;
    @NotBlank
    private BodyType bodyType;
    @NotBlank
    private int firstRegistration;
    @NotBlank
    private Colour colour;
    @NotBlank
    private double mileage;
    @NotBlank
    private Status status;
    @NotBlank
    private double pricePerDay;
    @ManyToOne
    private Branch branch;

}
