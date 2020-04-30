package basket.watch.backend.scraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Singleton
public class ScraperService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ItemScraper[] registeredScrapers;


    public ScraperService(ItemScraperArvutitark itemScraperArvutitark, ItemScraperDefault itemScraperDefault) {
        registeredScrapers = new ItemScraper[]{itemScraperArvutitark, itemScraperDefault};
    }


    public Optional<ScrapedItem> scrape(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            LOG.error(e.getMessage(), e);
            return Optional.empty();
        }

        String domain = uri.getHost();

        ScrapedItem scrapedItem = null;
        ItemScraper itemScraper = findByDomain(domain);
        if (itemScraper != null) {
            scrapedItem = itemScraper.scrapeUrl(url);
        }
        return Optional.ofNullable(scrapedItem);
    }

    private ItemScraper findByDomain(String domain) {
        for (ItemScraper itemScraper : registeredScrapers) {
            if (itemScraper.supports(domain)) {
                return itemScraper;
            }
        }
        throw new RuntimeException("scraper not found for domain " + domain);
    }
}
