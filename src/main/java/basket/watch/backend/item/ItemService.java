package basket.watch.backend.item;

import basket.watch.backend.common.entity.PriceHistory;
import basket.watch.backend.scraper.ScrapedItem;
import basket.watch.backend.scraper.ScraperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class ItemService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ItemRepository itemRepository;

    private final ScraperService scraperService;

    public Item create(String url) {

        Optional<Item> existingItem = itemRepository.findByUrl(url);

        if (existingItem.isPresent()) {
            return existingItem.get();
        }

        Item item = scrapeNew(url);
        return itemRepository.save(item);
    }

    private Item scrapeNew(String url) {
        Optional<ScrapedItem> scrapedItemOptional = scraperService.scrape(url);

        if (scrapedItemOptional.isEmpty()) {
            throw new RuntimeException("can not scrape url " + url);
        }

        Item item = new Item();
        item.setUrl(url);
        updateItemFormScrapedItem(scrapedItemOptional.get(), item);

        return item;
    }

    public Item update(Item item) {
        return scrapeForUpdate(item);
    }

    private Item scrapeForUpdate(Item item) {
        Optional<ScrapedItem> scrapedItemOptional = scraperService.scrape(item.getUrl());

        if (scrapedItemOptional.isEmpty()) {
            LOG.warn("update failed, scraper returned no object for {}", item.getUrl());
            return item;
        }

        updateItemFormScrapedItem(scrapedItemOptional.get(), item);

        return item;
    }

    private void updateItemFormScrapedItem(ScrapedItem scrapedItem, Item item) {
        item.setName(scrapedItem.getName());
        if (item.getPriceHistory() == null) {
            item.setPriceHistory(getNewPriceHistory(scrapedItem));
        } else {
            updatePriceHistory(item.getPriceHistory(), scrapedItem);
        }
    }

    private void updatePriceHistory(PriceHistory priceHistory, ScrapedItem scrapedItem) {
        priceHistory.setPrice(scrapedItem.getPrice());
        priceHistory.setPriceAt(scrapedItem.getZonedDateTime());

        if (scrapedItem.getPrice().compareTo(priceHistory.getPriceMin()) < 0) {
            priceHistory.setPriceMin(scrapedItem.getPrice());
            priceHistory.setPriceMinAt(scrapedItem.getZonedDateTime());
        } else if (scrapedItem.getPrice().compareTo(priceHistory.getPriceMax()) > 0) {
            priceHistory.setPriceMax(scrapedItem.getPrice());
            priceHistory.setPriceMaxAt(scrapedItem.getZonedDateTime());
        }

    }

    private PriceHistory getNewPriceHistory(ScrapedItem scrapedItem) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(scrapedItem.getPrice());
        priceHistory.setPriceAt(scrapedItem.getZonedDateTime());
        priceHistory.setPriceMin(scrapedItem.getPrice());
        priceHistory.setPriceMinAt(scrapedItem.getZonedDateTime());
        priceHistory.setPriceMax(scrapedItem.getPrice());
        priceHistory.setPriceMaxAt(scrapedItem.getZonedDateTime());
        return priceHistory;
    }
}
