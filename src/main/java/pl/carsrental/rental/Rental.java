package pl.carsrental.rental;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.carsrental.branch.Branch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Rental {

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Rental() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    private String webDomain;

    @NotBlank
    private String email;

    @NotBlank
    private String owner;

//    private String urlLogotype;

    @NotEmpty
    private static List<Branch> branchList;

}
