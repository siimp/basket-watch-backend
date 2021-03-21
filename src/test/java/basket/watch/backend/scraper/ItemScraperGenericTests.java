package basket.watch.backend.scraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
public class ItemScraperGenericTests {

    private ItemScraperGeneric scraper;

    @Inject
    private ResourceLoader resourceLoader;

    @Inject
    private ObjectMapper objectMapper;

    @Test
    public void testScrapeArvutitark() throws IOException {
        respondWith("item_arvutitark.html");
        Platform platform = PlatformUtils.parsePlatforms(resourceLoader).get("arvutitark.ee");
        Optional<ScrapedItem> scrapedItem = scraper.scrapeUrl("", platform);
        assertEquals("AMD Processor Ryzen 5 3600 3,6GH AM4 100-100000031BOX", scrapedItem.get().getName());
        assertEquals(new BigDecimal("188.90"), scrapedItem.get().getPrice());
    }

    @Test
    public void testScrapeKlick() throws IOException {
        respondWith("item_klick.html");
        Platform platform = PlatformUtils.parsePlatforms(resourceLoader).get("www.klick.ee");
        Optional<ScrapedItem> scrapedItem = scraper.scrapeUrl("", platform);
        assertEquals("Nutitelefon Google Pixel 4a, 6+128GB", scrapedItem.get().getName());
        assertEquals(new BigDecimal("459.99"), scrapedItem.get().getPrice());
    }

    @Test
    public void testScrapeKaup24() throws IOException {
        respondWith("item_kaup24.html");
        Platform platform = PlatformUtils.parsePlatforms(resourceLoader).get("kaup24.ee");
        Optional<ScrapedItem> scrapedItem = scraper.scrapeUrl("", platform);
        assertEquals("Asus VG259QM", scrapedItem.get().getName());
        assertEquals(new BigDecimal("367.00"), scrapedItem.get().getPrice());
    }

    @Test
    public void testScrape1A() throws IOException {
        respondWith("item_1A.html");
        Platform platform = PlatformUtils.parsePlatforms(resourceLoader).get("www.1a.ee");
        Optional<ScrapedItem> scrapedItem = scraper.scrapeUrl("", platform);
        assertEquals("Gigabyte AORUS NVMe Gen4 SSD 500GB", scrapedItem.get().getName());
        assertEquals(new BigDecimal("135.00"), scrapedItem.get().getPrice());
    }

    @Test
    public void testScrapeKrauta() throws IOException {
        respondWith("item_krauta.html");
        Platform platform = PlatformUtils.parsePlatforms(resourceLoader).get("www.k-rauta.ee");
        Optional<ScrapedItem> scrapedItem = scraper.scrapeUrl("", platform);
        assertEquals("Protsessor AMD Ryzen 5 5600X 3.7GHz", scrapedItem.get().getName());
        assertEquals(new BigDecimal("366.89"), scrapedItem.get().getPrice());
    }

    private void respondWith(String resource) throws IOException {
        Optional<InputStream> test = resourceLoader.getResourceAsStream(resource);
        String itemHtml = new String(test.get().readAllBytes(), StandardCharsets.UTF_8);

        CloseableHttpClient httpClientMock = apacheHttpClient();

        scraper = new ItemScraperGeneric(httpClientMock);
        when(httpClientMock.execute(any()).getEntity().getContent())
                .thenReturn(new ByteArrayInputStream(itemHtml.getBytes()));
    }

    @MockBean(CloseableHttpClient.class)
    public CloseableHttpClient apacheHttpClient() {
        return mock(CloseableHttpClient.class, Mockito.RETURNS_DEEP_STUBS);
    }


}
