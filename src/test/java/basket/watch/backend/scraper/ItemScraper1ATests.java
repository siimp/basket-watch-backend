package basket.watch.backend.scraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
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
public class ItemScraper1ATests {

    private static final String ITEM_HTML_RESOURCE = "item_1A.html";

    private String itemHtml;

    private ItemScraper1A scraper;

    @Inject
    private ResourceLoader resourceLoader;

    @Inject
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() throws IOException {
        Optional<InputStream> test = resourceLoader.getResourceAsStream(ITEM_HTML_RESOURCE);
        itemHtml = new String(test.get().readAllBytes(), StandardCharsets.UTF_8);

        CloseableHttpClient httpClientMock = apacheHttpClient();

        scraper = new ItemScraper1A(httpClientMock, objectMapper);
        when(httpClientMock.execute(any()).getEntity().getContent())
            .thenReturn(new ByteArrayInputStream(itemHtml.getBytes()));
    }

    @Test
    public void testScrapeSuccessfully() {
        Optional<ScrapedItem> scrapedItem = scraper.scrapeUrl("");
        assertEquals("Gigabyte AORUS NVMe Gen4 SSD 500GB", scrapedItem.get().getName());
        assertEquals(new BigDecimal("135.0"), scrapedItem.get().getPrice());
    }

    @MockBean(CloseableHttpClient.class)
    public CloseableHttpClient apacheHttpClient() {
        return mock(CloseableHttpClient.class, Mockito.RETURNS_DEEP_STUBS);
    }


}
