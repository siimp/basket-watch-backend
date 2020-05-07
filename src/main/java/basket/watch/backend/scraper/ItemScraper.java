package basket.watch.backend.scraper;

import java.util.Optional;

public interface ItemScraper {

    Optional<ScrapedItem> scrapeUrl(String url);

    boolean supports(String domain);

}
