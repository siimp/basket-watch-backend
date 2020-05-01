package basket.watch.backend.basket;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;

@Singleton
@RequiredArgsConstructor
public class BasketPriceUpdateJob {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final int PAGE_SIZE = 20;

    private final BasketRepository basketRepository;

    private final BasketService basketService;

    private final EntityManager entityManager;

    @Transactional
    @Scheduled(cron = "0 0 5 * * ?")
    void execute() {
        LOG.info("starting to update basket prices");

        Page<Basket> page = basketRepository.findAll(getPageable(0));
        for (int pageNumber = 0; pageNumber < page.getTotalPages(); pageNumber++, page = basketRepository.findAll(getPageable(pageNumber))) {
            for (Basket basket: page.getContent()) {
                basketService.updatePrice(basket);
            }
            basketRepository.saveAll(page.getContent());
            entityManager.flush();
            entityManager.clear();
            LOG.info("updated {} baskets", page.getNumberOfElements());
        }

        LOG.info("finished updating basket prices");
    }

    private Pageable getPageable(int pageNumber) {
        return Pageable.from(pageNumber, PAGE_SIZE, Sort.of(Sort.Order.asc("createdAt")));
    }
}
