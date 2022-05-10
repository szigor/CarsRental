package pl.carsrental.employee;

import lombok.Data;
import pl.carsrental.branch.Branch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String surname;
    @NotBlank
    private Stand standing;
    @ManyToOne(optional = false)
    private Branch branch;
}
