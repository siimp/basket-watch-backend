package basket.watch.backend.scraper;

public interface ItemScraper {

    ScrapedItem scrapeUrl(String url);

    boolean supports(String domain);

}
