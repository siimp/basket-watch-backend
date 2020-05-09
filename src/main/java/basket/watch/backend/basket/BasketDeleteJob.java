package basket.watch.backend.basket;

import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class BasketDeleteJob {

    private final BasketRepository basketRepository;

    private final BasketDeleteJobProperties properties;

    @Scheduled(cron = "${basket-watch.job.basket-delete.cron}")
    @Transactional
    void execute() {
        log.info("starting to delete old baskets older than {} weeks", properties.getBasketValidityInWeeks());
        int deletedBaskets = basketRepository.deleteAllByUpdatedAtLessThan(LocalDateTime.now()
                .minusWeeks(properties.getBasketValidityInWeeks()));
        log.info("deleted {} old baskets", deletedBaskets);
    }
}
