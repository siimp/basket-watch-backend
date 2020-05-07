package basket.watch.backend.scraper;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Singleton
@Slf4j
public class ScraperService {

    private final ItemScraper[] registeredScrapers;


    public ScraperService(ItemScraperArvutitark itemScraperArvutitark, ItemScraperDefault itemScraperDefault) {
        registeredScrapers = new ItemScraper[]{itemScraperArvutitark, itemScraperDefault};
    }


    public Optional<ScrapedItem> scrape(String url) {
        log.info("scraping url {}", url);
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }

        String domain = uri.getHost();

        ItemScraper itemScraper = findByDomain(domain);
        return Optional.ofNullable(itemScraper.scrapeUrl(url));
    }

    private ItemScraper findByDomain(String domain) {
        for (ItemScraper itemScraper : registeredScrapers) {
            if (itemScraper.supports(domain)) {
                return itemScraper;
            }
        }
        throw new ScraperNotFoundException();
    }
}
