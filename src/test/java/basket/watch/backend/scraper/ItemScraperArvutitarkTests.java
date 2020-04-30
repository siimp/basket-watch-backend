package basket.watch.backend.scraper;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;
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
public class ItemScraperArvutitarkTests {

    private static final String ITEM_HTML_RESOURCE = "item_arvutitark.html";

    private String itemHtml;

    private ItemScraperArvutitark itemScraperArvutitark;

    @Inject
    private ResourceLoader resourceLoader;

    @BeforeEach
    public void beforeEach() throws IOException {
        Optional<InputStream> test = resourceLoader.getResourceAsStream(ITEM_HTML_RESOURCE);
        itemHtml = new String(test.get().readAllBytes(), StandardCharsets.UTF_8);

        HttpClient httpClientMock = httpClient();
        BlockingHttpClient blockingHttpClient = blockingHttpClient();

        itemScraperArvutitark = new ItemScraperArvutitark(httpClientMock);
        when(httpClientMock.toBlocking()).thenReturn(blockingHttpClient);
        when(blockingHttpClient.retrieve(any(String.class))).thenReturn(itemHtml);
    }


    @Test
    public void testScrapeSuccessfully() {
        ScrapedItem scrapedItem = itemScraperArvutitark.scrapeUrl("");
        assertEquals("AMD Processor Ryzen 5 3600 3,6GH AM4 100-100000031BOX", scrapedItem.getName());
        assertEquals(new BigDecimal("188.90"), scrapedItem.getPrice());
    }

    @MockBean(HttpClient.class)
    public HttpClient httpClient() {
        return mock(HttpClient.class);
    }

    @MockBean(BlockingHttpClient.class)
    public BlockingHttpClient blockingHttpClient() {
        return mock(BlockingHttpClient.class);
    }

}
