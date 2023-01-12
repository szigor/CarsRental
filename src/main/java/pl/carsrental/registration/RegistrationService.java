package pl.carsrental.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.carsrental.client.Client;
import pl.carsrental.client.ClientRole;
import pl.carsrental.client.ClientService;
import pl.carsrental.email.EmailSender;
import pl.carsrental.registration.token.ConfirmationToken;
import pl.carsrental.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final ClientService clientService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public void register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = clientService.signUpUser(new Client(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), ClientRole.USER));

        String link = "http://localhost:8080/registration/confirm?token=" + token;

        emailSender.send(request.getEmail(), buildEmail(request.getFirstName(), link));

    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        clientService.enableAppUser(confirmationToken.getClient().getEmail());
        return "confirmed-email-template";
    }

    private String buildEmail(String name, String link) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <style>\n" +
                "        .confirm {\n" +
                "            background-color: black;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            text-align: center;\n" +
                "            font-family: Arial,serif;\n" +
                "            font-weight: bold;\n" +
                "            color: white;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            font-family: Arial,serif;\n" +
                "            margin-bottom: 8px;\n" +
                "            font-size: large;\n" +
                "            color: black;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            text-align: center;\n" +
                "            font-size: xx-large;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            margin-bottom: 8px;\n" +
                "        }\n" +
                "\n" +
                "        .expire {\n" +
                "            text-align: center;\n" +
                "            font-size: x-small;\n" +
                "            color: grey;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"confirm\">\n" +
                "    <h1>Confirm your email</h1>\n" +
                "</div>\n" +
                "<div>\n" +
                "    <div>\n" +
                "        <br>\n" +
                "        <p>Hi " + name + ",</p>\n" +
                "        <p>Thank you for registering. Please click on the below link to activate your account:</p>\n" +
                "        <div class=\"container\">\n" +
                "            <a class=\"button blue\" href=\"" + link + "\">Activate now</a>\n" +
                "        </div>\n" +
                "        <p class=\"expire\">Link will expire in 15 minutes.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }
}
