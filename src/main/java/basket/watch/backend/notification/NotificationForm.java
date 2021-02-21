package basket.watch.backend.notification;

import io.micronaut.validation.Validated;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Validated
@Data
public class NotificationForm {

    @NotBlank
    @Email
    private String email;
}
