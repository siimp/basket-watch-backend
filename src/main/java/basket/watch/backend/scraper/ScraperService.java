package basket.watch.backend.scraper;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Singleton
@Slf4j
public class ScraperService {

    private final ItemScraper[] registeredScrapers;


    public ScraperService(ItemScraperArvutitark itemScraperArvutitark,
                          ItemScraper1A itemScraper1A) {
        registeredScrapers = new ItemScraper[]{itemScraperArvutitark, itemScraper1A};
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
        return itemScraper.scrapeUrl(url);
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
