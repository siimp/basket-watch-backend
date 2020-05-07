package basket.watch.backend.scraper;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Optional;

@Singleton
public class ItemScraperDefault implements ItemScraper {
    @Override
    public Optional<ScrapedItem> scrapeUrl(String url) {
        ScrapedItem scrapedItem = new ScrapedItem();
        scrapedItem.setName("Item not found from url " + url);
        scrapedItem.setPrice(BigDecimal.ZERO);
        return Optional.of(scrapedItem);
    }

    @Override
    public boolean supports(String domain) {
        return true;
    }
}
