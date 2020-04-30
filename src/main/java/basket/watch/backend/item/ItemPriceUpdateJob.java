package basket.watch.backend.item;

import basket.watch.backend.common.ThreadUtils;
import io.micronaut.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;

@Singleton
@RequiredArgsConstructor
public class ItemPriceUpdateJob {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ItemRepository itemRepository;

    private final ItemService itemService;

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?")
    void execute() {
        LOG.info("starting to update basket item prices");
        Iterable<Item> items = itemRepository.findAll();
        for(Item item: items) {
            LOG.info("updating item {}", item.getId());
            itemService.update(item);
            ThreadUtils.randomSleep();
        }
        itemRepository.saveAll(items);
        LOG.info("finished updating prices");
    }
}
