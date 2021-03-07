package basket.watch.backend.scraper;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Optional;

@Singleton
@Slf4j
public class ItemScraperArvutitark extends AbstractItemScraper {

    private static final String START_SECTION = "<section class=\"body\">";


    public ItemScraperArvutitark(CloseableHttpClient apacheHttpClient) {
        super(apacheHttpClient);
    }

    @Override
    protected Optional<ScrapedItem> parseResponse(String response) {
        Optional<String> nameOptional = getName(response);
        Optional<BigDecimal> priceOptional = getPrice(response);
        if (nameOptional.isPresent() && priceOptional.isPresent()) {
            return Optional.of(getScrapedItem(nameOptional.get(), priceOptional.get()));
        }

        return Optional.empty();
    }

    private ScrapedItem getScrapedItem(String name, BigDecimal price) {
        ScrapedItem scrapedItem = new ScrapedItem();
        scrapedItem.setName(name);
        scrapedItem.setPrice(price);
        return scrapedItem;
    }

    protected Optional<String> getName(String response) {
        int startSectionIndex = response.indexOf(START_SECTION);

        String start = "<h1>";
        int startIndex = response.indexOf(start, startSectionIndex) + start.length();
        int endIndex = response.indexOf("</h1>", startIndex);

        if (endIndex > -1) {
            return Optional.of(response.substring(startIndex, endIndex));
        }
        return Optional.empty();
    }

    protected Optional<BigDecimal> getPrice(String response) {
        int startSectionIndex = response.indexOf(START_SECTION);

        String start = "<div class=\"product-price\">";
        int startIndex = response.indexOf(start, startSectionIndex) + start.length();
        int endIndex = response.indexOf('€', startIndex);

        if (endIndex > -1) {
            return Optional.of(new BigDecimal(response.substring(startIndex, endIndex)));
        }

        return Optional.empty();
    }

}
