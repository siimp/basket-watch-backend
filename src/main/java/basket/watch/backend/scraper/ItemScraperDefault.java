package basket.watch.backend.scraper;

import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public class ItemScraperDefault implements ItemScraper {
    @Override
    public ScrapedItem scrapeUrl(String url) {
        ScrapedItem scrapedItem = new ScrapedItem();
        scrapedItem.setName("default name");
        scrapedItem.setPrice(BigDecimal.ONE);
        return scrapedItem;
    }

    @Override
    public boolean supports(String domain) {
        return true;
    }
}
