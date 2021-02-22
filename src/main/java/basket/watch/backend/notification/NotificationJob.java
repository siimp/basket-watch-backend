package basket.watch.backend.notification;

import basket.watch.backend.basket.Basket;
import basket.watch.backend.basket.BasketRepository;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class NotificationJob {

    private static final String ERROR_EMAIL_MESSAGE = "Our services are experiencing difficulties at the moment, please be patient.";

    private static final String SUBJECT = "Basket price update";

    private final BasketRepository basketRepository;

    private final EmailService emailService;

    private final ResourceLoader resourceLoader;

    @Transactional
    @Scheduled(cron = "${basket-watch.job.notification.cron}")
    void execute() {
        log.info("starting notification job");

        List<Basket> baskets = basketRepository
                .findAllByNotificationSubscribedAndNotificationShouldSendNotificationAndNotificationShouldSendNotificationAt(
                        Boolean.TRUE, Boolean.TRUE, LocalDate.now());
        for (Basket basket : baskets) {
            if (basket.getNotification() != null) {
                emailService.sendNotification(getMessage(basket), SUBJECT, basket.getNotification().getEmail());
                basket.getNotification().setShouldSendNotification(Boolean.FALSE);
                basketRepository.save(basket);
            }
        }

        log.info("finished notification job");
    }

    private String getMessage(Basket basket) {
        Optional<InputStream> templateOptional =
                resourceLoader.getResourceAsStream("templates/email/notification.html");
        if (templateOptional.isEmpty()) {
            return ERROR_EMAIL_MESSAGE;
        }

        try {
            String text = new String(templateOptional.get().readAllBytes(), StandardCharsets.UTF_8);
            text = text.replace("{{UUID}}", basket.getUuid().toString());
            text = text.replace("{{PRICE}}", basket.getPriceHistory().getPrice().toString());
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ERROR_EMAIL_MESSAGE;

    }
}
