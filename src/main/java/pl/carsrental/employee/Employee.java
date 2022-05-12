package pl.carsrental.employee;

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
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String surname;
//    @NotBlank
    private Stand standing;
//    @ManyToOne(optional = false)
    @ManyToOne
    private Branch branch;

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Employee() {
    }
}
