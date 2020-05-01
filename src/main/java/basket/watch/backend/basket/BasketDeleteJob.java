package basket.watch.backend.basket;

import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.lang.invoke.MethodHandles;
import java.time.ZonedDateTime;

@Singleton
@RequiredArgsConstructor
public class BasketDeleteJob {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final int BASKET_VALIDITY_IN_WEEKS = 8;

    private final BasketRepository basketRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    void execute() {
        LOG.info("starting to delete old baskets");
        int deletedBaskets = basketRepository.deleteAllByCreatedAtLessThan(ZonedDateTime.now().plusWeeks(BASKET_VALIDITY_IN_WEEKS));
        LOG.info("deleted {} old baskets", deletedBaskets);
    }
}
