package basket.watch.backend.common.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Data
public class Notification {

    private String email;

    private Boolean subscribed;

    private Boolean shouldSendNotification;

    private LocalDate shouldSendNotificationAt;
}
