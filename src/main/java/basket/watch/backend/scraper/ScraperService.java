package basket.watch.backend.scraper;

import io.micronaut.core.io.ResourceLoader;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

@Singleton
@Slf4j
public class ScraperService {

    private final Map<String, Platform> domainPlatform;

    private final ItemScraperGeneric itemScraperGeneric;

    public ScraperService(ResourceLoader resourceLoader,
                          ItemScraperGeneric itemScraperGeneric) {
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

        return itemScraperGeneric.scrapeUrl(url, platform);
    }

}
