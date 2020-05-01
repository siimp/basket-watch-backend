package basket.watch.backend.scraper;

import io.micronaut.http.client.HttpClient;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
@RequiredArgsConstructor
public class ItemScraperArvutitark implements ItemScraper {

    private static final String DOMAIN = "arvutitark.ee";

    private static final String START_SECTION = "<section class=\"body\">";

    private final HttpClient httpClient;

    @Override
    public ScrapedItem scrapeUrl(String url) {
        String response = httpClient.toBlocking().retrieve(url);
        String name = getName(response);
        BigDecimal price = getPrice(response);
        ScrapedItem scrapedItem = new ScrapedItem();
        scrapedItem.setName(name);
        scrapedItem.setPrice(price);
        return scrapedItem;
    }

    private String getName(String response) {
        Integer startSectionIndex = response.indexOf(START_SECTION);

        String start = "<h1>";
        Integer startIndex = response.indexOf(start, startSectionIndex) + start.length();
        Integer endIndex = response.indexOf("</h1>", startIndex);
        return response.substring(startIndex, endIndex);
    }

    private BigDecimal getPrice(String response) {
        Integer startSectionIndex = response.indexOf(START_SECTION);

        String start = "<div class=\"product-price\">";
        Integer startIndex = response.indexOf(start, startSectionIndex) + start.length();
        Integer endIndex = response.indexOf('€', startIndex);
        return new BigDecimal(response.substring(startIndex, endIndex));
    }

    @Override
    public boolean supports(String domain) {
        return DOMAIN.equals(domain);
    }
}
