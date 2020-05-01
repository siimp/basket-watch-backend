package basket.watch.backend.item;

import basket.watch.backend.common.ThreadUtils;
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
public class ItemPriceUpdateJob {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final int PAGE_SIZE = 20;

    private final ItemRepository itemRepository;

    private final ItemService itemService;

    private final EntityManager entityManager;

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?")
    void execute() {
        LOG.info("starting to update basket item prices");
        Page<Item> page = itemRepository.findAll(getPageable(0));
        for (int pageNumber = 0; pageNumber < page.getTotalPages(); pageNumber++, page = itemRepository.findAll(getPageable(pageNumber))) {
            for (Item item: page.getContent()) {
                itemService.update(item);
                ThreadUtils.randomSleep();
            }
            itemRepository.saveAll(page.getContent());
            entityManager.flush();
            entityManager.clear();
            LOG.info("updated {} items", page.getNumberOfElements());
        }

        LOG.info("finished updating prices");
    }

    private Pageable getPageable(int pageNumber) {
        return Pageable.from(pageNumber, PAGE_SIZE, Sort.of(Sort.Order.asc("id")));
    }
}
