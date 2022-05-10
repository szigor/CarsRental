package pl.carsrental.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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

}
