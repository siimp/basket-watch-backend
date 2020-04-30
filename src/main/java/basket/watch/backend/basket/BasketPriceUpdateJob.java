package basket.watch.backend.basket;

import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.lang.invoke.MethodHandles;

@Singleton
@RequiredArgsConstructor
public class BasketPriceUpdateJob {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final BasketRepository basketRepository;

    @Scheduled(cron = "0 0 5 * * ?")
    void execute() {
        LOG.info("starting to update basket prices");
    }
}
