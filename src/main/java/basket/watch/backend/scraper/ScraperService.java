package basket.watch.backend.scraper;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
@Slf4j
public class ScraperService {

    private final Map<String, Platform> domainPlatform;

    private final Map<String, ItemScraper> scraperClassScraper = new HashMap<>();

    private final ItemScraperGeneric itemScraperGeneric;

    public ScraperService(ItemScraper1A itemScraper1A,
                          ResourceLoader resourceLoader,
                          ItemScraperGeneric itemScraperGeneric) {
        this.scraperClassScraper.put("ItemScraper1A", itemScraper1A);
        this.itemScraperGeneric = itemScraperGeneric;
        this.domainPlatform = PlatformUtils.parsePlatforms(resourceLoader);
    }

    public Optional<ScrapedItem> scrape(String url) {
        log.info("scraping url {}", url);
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }

        String domain = uri.getHost();

        Platform platform = domainPlatform.get(domain);

        if (platform == null) {
            return Optional.empty();
        }

        if (StringUtils.hasText(platform.getScraperClass())) {
            ItemScraper itemScraper = scraperClassScraper.get(platform.getScraperClass());
            if (itemScraper == null) {
                return Optional.empty();
            }
            return itemScraper.scrapeUrl(url);
        }

        return itemScraperGeneric.scrapeUrl(url, platform);
    }

}
