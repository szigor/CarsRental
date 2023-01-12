package pl.carsrental.registration;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
