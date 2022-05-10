package pl.carsrental.rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.carsrental.branch.Branch;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @Column(name = "web_domain")
    private String webDomain;

    @NotBlank

    private String email;

    @NotBlank
    private String owner;

//    private String urlLogotype;

    @NotEmpty
    private static List<Branch> branchList;


    @Override
    public String toString() {
        return "Rental{" +
                "name='" + name + '\'' +
                ", webDomain='" + webDomain + '\'' +
                ", email='" + email + '\'' +
                ", owner='" + owner + '\'' +
                ", branchList=" + branchList +
                '}';
    }
}
