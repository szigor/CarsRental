package pl.carsrental.employee;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.carsrental.branch.Branch;
import pl.carsrental.hire.Hire;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

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

    @OneToMany(mappedBy = "employee")
    private List<Hire> hireList;

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Employee() {
    }
}
