package basket.watch.backend.scraper;

import io.micronaut.core.io.IOUtils;
import io.micronaut.core.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class ItemScraperArvutitark implements ItemScraper {

    private static final String DOMAIN = "arvutitark.ee";

    private static final String START_SECTION = "<section class=\"body\">";

    // micronaut HttpClient fails with "Request URI specifies no host to connect to" from class DefaultHttpClient after redirect with relative Location header
    private final CloseableHttpClient apacheHttpClient;

    @Override
    public Optional<ScrapedItem> scrapeUrl(String url) {
        String response = getResponse(url);

        if (!StringUtils.hasText(response)) {
            return Optional.empty();
        }

        String name = getName(response);
        BigDecimal price = getPrice(response);
        ScrapedItem scrapedItem = new ScrapedItem();
        scrapedItem.setName(name);
        scrapedItem.setPrice(price);
        return Optional.of(scrapedItem);
    }

    private String getResponse(String url) {
        HttpGet get = new HttpGet(url);
        try {
            CloseableHttpResponse response = apacheHttpClient.execute(get);
            return IOUtils.readText(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return null;
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
