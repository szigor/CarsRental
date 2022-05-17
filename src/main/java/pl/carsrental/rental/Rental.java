package pl.carsrental.rental;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.stereotype.Component;
import pl.carsrental.branch.Branch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Component
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Rental {

    @SuppressWarnings("unused") //hibernate tego potrzebuje
    protected Rental() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String webDomain;

    @NotBlank
    private String email;

    @NotBlank
    private String owner;

//    private String urlLogotype;

    @OneToMany(mappedBy = "rental")
    private List<Branch> branches;

    @Builder
    private Rental(Long id, @NotBlank String name, String webDomain, @NotBlank String email, @NotBlank String owner, @Singular List<Branch> branches) {
        this.id = id;
        this.name = name;
        this.webDomain = webDomain;
        this.email = email;
        this.owner = owner;
        this.branches = branches;
        branches.forEach(branch -> {branch.setRental(this);});
    }
}
