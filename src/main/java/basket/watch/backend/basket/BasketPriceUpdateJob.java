package basket.watch.backend.basket;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class BasketPriceUpdateJob {

    private static final int PAGE_SIZE = 20;

    private final BasketRepository basketRepository;

    private final BasketService basketService;

    private final EntityManager entityManager;

    @Transactional
    @Scheduled(cron = "${basket-watch.job.basket-price-update.cron}")
    void execute() {
        log.info("starting to update basket prices");

        Page<Basket> page = basketRepository.findAll(getPageable(0));
        for (int pageNumber = 0; pageNumber < page.getTotalPages(); pageNumber++, page = basketRepository.findAll(getPageable(pageNumber))) {
            for (Basket basket: page.getContent()) {
                basketService.updatePrice(basket);
            }
            basketRepository.saveAll(page.getContent());
            entityManager.flush();
            entityManager.clear();
            log.info("updated {} baskets", page.getNumberOfElements());
        }

        log.info("finished updating basket prices");
    }

    private Pageable getPageable(int pageNumber) {
        return Pageable.from(pageNumber, PAGE_SIZE, Sort.of(Sort.Order.asc("createdAt")));
    }
}
