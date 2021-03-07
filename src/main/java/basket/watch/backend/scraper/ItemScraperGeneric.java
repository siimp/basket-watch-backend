package basket.watch.backend.scraper;

import io.micronaut.core.io.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Singleton
@Slf4j
public class ItemScraperGeneric {

    private final CloseableHttpClient apacheHttpClient;

    public ItemScraperGeneric(CloseableHttpClient apacheHttpClient) {
        this.apacheHttpClient = apacheHttpClient;
    }

    public Optional<ScrapedItem> scrapeUrl(String url, Platform platform) {
        String response = getResponse(url);
        if (response == null) {
            return Optional.empty();
        }

        ScrapedItem scrapedItem = new ScrapedItem();
        String name = getName(response, platform.getNameXpath());
        scrapedItem.setName(name);
        BigDecimal price = getPrice(response, platform.getPriceXpath());
        scrapedItem.setPrice(price);
        return Optional.of(scrapedItem);
    }

    private String getName(String response, String nameXpath) {
        return null;
    }

    private BigDecimal getPrice(String response, String priceXpath) {
        return null;
    }

    private String getResponse(String url) {
        HttpGet get = new HttpGet(url);
        get.setHeader("Content-Type", "text/html; charset=UTF-8");
        try {
            CloseableHttpResponse response = apacheHttpClient.execute(get);
            return IOUtils.readText(new BufferedReader(new InputStreamReader(response.getEntity().getContent(),
                    StandardCharsets.UTF_8)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
