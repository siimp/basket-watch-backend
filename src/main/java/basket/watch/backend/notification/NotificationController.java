package basket.watch.backend.notification;

import basket.watch.backend.basket.BasketService;
import basket.watch.backend.common.entity.Notification;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@Validated
@Controller("/basket/{basketUuid}/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    private final BasketService basketService;

    private final EmailService emailService;

    @Post
    public HttpResponse<Void> post(@NotNull final UUID basketUuid, final NotificationForm notificationForm) {
        log.info("subscribing to notification for basket {}", basketUuid);

        if (basketService.existsByUuid(basketUuid)) {
            Notification notification = new Notification();
            notification.setEmail(notificationForm.getEmail());
            notification.setSubscribed(Boolean.TRUE);
            notificationService.save(basketUuid, notification);
            return HttpResponse.ok();
        }

        log.warn("unable to find basket with uuid {}", basketUuid);
        return HttpResponse.badRequest();
    }

    @Get("/unsubscribe")
    public HttpResponse<Void> unsubscribe(@NotNull final UUID basketUuid) {
        log.info("unsubscribing notifications for basket {}", basketUuid);

        if (basketService.existsByUuid(basketUuid)) {
            notificationService.unsubscribe(basketUuid);
        }

        return HttpResponse.ok();
    }

    @Get("/test-send")
    public HttpResponse<Void> send(@NotNull final UUID basketUuid) {
        log.info("unsubscribing notifications for basket {}", basketUuid);

        emailService.sendNotification("test",
                basketService.findByUuid(basketUuid).get().getNotification().getEmail());

        return HttpResponse.ok();
    }
}
