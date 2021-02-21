package basket.watch.backend.notification;

import basket.watch.backend.basket.Basket;
import basket.watch.backend.basket.BasketRepository;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class NotificationJob {

    private final BasketRepository basketRepository;

    private final EmailService emailService;

    @Transactional
    @Scheduled(cron = "${basket-watch.job.notification.cron}")
    void execute() {
        log.info("starting notification job");

        List<Basket> baskets = basketRepository
                .findAllByNotificationSubscribedAndNotificationShouldSendNotificationAndNotificationShouldSendNotificationAt(
                        Boolean.TRUE, Boolean.TRUE, LocalDate.now());
        for (Basket basket : baskets) {
            if (basket.getNotification() != null) {
                emailService.sendNotification(getMessage(basket), basket.getNotification().getEmail());
                basket.getNotification().setShouldSendNotification(Boolean.FALSE);
                basketRepository.save(basket);
            }
        }

        log.info("finished notification job");
    }

    private String getMessage(Basket basket) {
        String url = String.format("https://basket-watch.siimp.ee/basket/%s", basket.getUuid().toString());
        return String.format("Your basket <a href=\"%s\">%s</a> price is now at %s €",
                url, basket.getUuid().toString(), basket.getPriceHistory().getPrice());
    }
}
