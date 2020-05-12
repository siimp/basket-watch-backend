package basket.watch.backend.scraper;

import io.micronaut.core.io.IOUtils;
import io.micronaut.core.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractItemScraper implements ItemScraper {

    private final CloseableHttpClient apacheHttpClient;

    @Override
    public Optional<ScrapedItem> scrapeUrl(String url) {
        String response = getResponse(url);

        if (!StringUtils.hasText(response)) {
            log.warn("no response from {}", url);
            return Optional.empty();
        }

        Optional<ScrapedItem> result = parseResponse(response);
        if (!result.isPresent()) {
            log.warn("could not scrape item from {}", url);
        }

        return result;
    }

    protected abstract Optional<ScrapedItem> parseResponse(String response);


    private String getResponse(String url) {
        HttpGet get = new HttpGet(url);
        get.setHeader("Content-Type","text/html; charset=UTF-8");
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
