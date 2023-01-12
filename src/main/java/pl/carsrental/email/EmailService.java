package pl.carsrental.email;

import lombok.RequiredArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Value("${simple.javamail.host}")
    private String host;
    @Value("${simple.javamail.username}")
    private String username;
    @Value("${simple.javamail.password}")
    private String password;
    @Value("${simple.javamail.port}")
    private Integer port;
    @Override
    @Async
    public void send(String to, String email) {

        Email emailBuilder = EmailBuilder.startingBlank()
                .from(username)
                .to(to)
                .withSubject("Confirm your email")
                .withHTMLText(email)
                .buildEmail();

        MailerBuilder
                .withSMTPServer(host, port, username, password)
                .buildMailer()
                .sendMail(emailBuilder);

        LOGGER.info("Attempting to send email..");

    }
}
