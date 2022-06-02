package pl.carsrental.employee;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String surname;

    private Integer tel;

//    @NotBlank
    @Enumerated(EnumType.STRING)
    private Stand standing;

    @NotBlank
    private String email;

    @NotBlank
    private String address;

//    @ManyToOne(optional = false)
    @ManyToOne
    private Branch branch;

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Employee() {
    }
}
