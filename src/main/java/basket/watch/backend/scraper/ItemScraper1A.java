package basket.watch.backend.scraper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Optional;

@Singleton
@Slf4j
public class ItemScraper1A extends AbstractItemScraper {

    private static final String DOMAIN = "www.1a.ee";

    private static final String SCRIPT_START_TAG = "<script type='application/ld+json'>";

    private static final String SCRIPT_END_TAG = "</script>";

    private ObjectMapper objectMapper;


    public ItemScraper1A(CloseableHttpClient apacheHttpClient, ObjectMapper objectMapper) {
        super(apacheHttpClient);
        this.objectMapper = objectMapper;
    }

    @Override
    protected Optional<ScrapedItem> parseResponse(String response) {
        int scriptStartIndex = response.indexOf(SCRIPT_START_TAG);
        int scriptEndIndex = response.indexOf(SCRIPT_END_TAG, scriptStartIndex);
        String json = response.substring(scriptStartIndex + SCRIPT_START_TAG.length(), scriptEndIndex);
        try {
            Item1A item1A = objectMapper.readValue(json, Item1A.class);
            return getScrapedItem(item1A);
        } catch (JsonProcessingException e) {
            log.warn("could not parse json from url {}", json);
        }
        return Optional.empty();
    }

    private Optional<ScrapedItem> getScrapedItem(Item1A item1A) {
        if (StringUtils.hasText(item1A.getName()) && StringUtils.hasText(item1A.getOffers().getPrice())) {
            ScrapedItem scrapedItem = new ScrapedItem();
            scrapedItem.setName(item1A.getName());
            scrapedItem.setPrice(new BigDecimal(item1A.getOffers().getPrice()));
            return Optional.of(scrapedItem);
        }
        return Optional.empty();
    }

    @Override
    public boolean supports(String domain) {
        return DOMAIN.equals(domain);
    }

}

@Data
class Item1A {
    private String name;
    private Offers offers;
}

@Data
class Offers {
    private String price;
}
