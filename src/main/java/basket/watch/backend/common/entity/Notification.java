package basket.watch.backend.common.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Notification {

    private String email;

    private Boolean subscribed;
}
