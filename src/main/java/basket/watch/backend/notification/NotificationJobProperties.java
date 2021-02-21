package basket.watch.backend.notification;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Context
@ConfigurationProperties("basket-watch.job.notification")
@Data
public class NotificationJobProperties {

    @NotBlank
    private String cron;
}
