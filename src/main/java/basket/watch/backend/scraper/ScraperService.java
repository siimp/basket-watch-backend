package basket.watch.backend.scraper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
@Slf4j
public class ScraperService {

    private static final String PLATFORMS_JSON_RESOURCE = "static/platforms.json";

    private final Map<String, Platform> domainPlatform = new HashMap<>();

    private final Map<String, ItemScraper> scraperClassScraper = new HashMap<>();

    private final ItemScraperGeneric itemScraperGeneric;

    public ScraperService(ItemScraperArvutitark itemScraperArvutitark,
                          ItemScraper1A itemScraper1A,
                          ResourceLoader resourceLoader,
                          ObjectMapper objectMapper,
                          ItemScraperGeneric itemScraperGeneric) {
        this.scraperClassScraper.put("ItemScraperArvutitark", itemScraperArvutitark);
        this.scraperClassScraper.put("ItemScraper1A", itemScraper1A);
        this.itemScraperGeneric = itemScraperGeneric;
        parsePlatforms(resourceLoader, objectMapper);
    }

    private void parsePlatforms(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        Optional<InputStream> json = resourceLoader.getResourceAsStream(PLATFORMS_JSON_RESOURCE);
        if (json.isPresent()) {
            try {
                List<Platform> platforms = objectMapper.readValue(json.get(), new TypeReference<>() {
                });
                for (Platform platform : platforms) {
                    domainPlatform.put(platform.getDomain(), platform);
                }
            } catch (IOException e) {
                log.error("failed to process platforms json {}", e.getMessage());
            }
        }
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
