package basket.watch.backend.scraper;

import io.micronaut.core.io.IOUtils;
import io.micronaut.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
@Slf4j
public class ItemScraperGeneric {

    private static final Pattern NON_PRICE_VALUE_SYMBOL = Pattern.compile("[^0-9]");

    private final CloseableHttpClient apacheHttpClient;

    public ItemScraperGeneric(CloseableHttpClient apacheHttpClient) {
        this.apacheHttpClient = apacheHttpClient;
    }

    public Optional<ScrapedItem> scrapeUrl(String url, Platform platform) {
        String response = getResponse(url);
        if (response == null) {
            return Optional.empty();
        }
        Document htmlDocument = Jsoup.parse(response);

        ScrapedItem scrapedItem = new ScrapedItem();
        scrapedItem.setName(getName(htmlDocument, platform));
        scrapedItem.setPrice(getPrice(htmlDocument, platform));
        return Optional.of(scrapedItem);
    }

    private String getName(Document htmlDocument, Platform platform) {
        Element name = htmlDocument.select(platform.getNameJsoupSelector()).first();
        if (StringUtils.hasText(platform.getNameJsoupAttributeKey())) {
            return name.attr(platform.getNameJsoupAttributeKey());
        }
        return name.text();
    }

    private BigDecimal getPrice(Document htmlDocument, Platform platform) {
        Element priceElement = htmlDocument.select(platform.getPriceJsoupSelector()).first();
        String price;
        if (StringUtils.hasText(platform.getPriceJsoupAttributeKey())) {
            price = priceElement.attr(platform.getPriceJsoupAttributeKey());
        } else {
            price = priceElement.text();
        }

        int endIndex = 0;
        for (char c : price.toCharArray()) {
            if (!Character.isDigit(c) && c != '.' && c != ',') {
                break;
            }
            endIndex++;
        }
        return new BigDecimal(price.substring(0, endIndex));
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
