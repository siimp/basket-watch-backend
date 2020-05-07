package basket.watch.backend.item;

import basket.watch.backend.common.ThreadUtils;
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
public class ItemPriceUpdateJob {

    private static final int PAGE_SIZE = 20;

    private final ItemRepository itemRepository;

    private final ItemService itemService;

    private final EntityManager entityManager;

    @Transactional(dontRollbackOn = RuntimeException.class)
    @Scheduled(cron = "${basket-watch.job.item-price-update.cron}")
    void execute() {
        log.info("starting to update item prices");
        Page<Item> page = itemRepository.findAll(getPageable(0));
        for (int pageNumber = 0; pageNumber < page.getTotalPages(); pageNumber++, page = itemRepository.findAll(getPageable(pageNumber))) {
            for (Item item: page.getContent()) {
                try {
                    itemService.update(item);
                } catch (RuntimeException e) {
                    log.error("update failed for item {}", item.getId());
                }
                ThreadUtils.randomSleep();
            }
            itemRepository.saveAll(page.getContent());
            entityManager.flush();
            entityManager.clear();
            log.info("updated {} items", page.getNumberOfElements());
        }

        log.info("finished updating item prices");
    }

    private Pageable getPageable(int pageNumber) {
        return Pageable.from(pageNumber, PAGE_SIZE, Sort.of(Sort.Order.asc("id")));
    }
}
