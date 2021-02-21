package basket.watch.backend.notification;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Context
@ConfigurationProperties("basket-watch.mail")
@Data
public class MailProperties {

    @Email
    private String from;

    @NotBlank
    private String smtpHost;
}
