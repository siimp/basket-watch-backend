package basket.watch.backend.scraper;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@MicronautTest
public class ScraperServiceTests {

    @Inject
    private ItemScraper1A itemScraper1A;

    @Inject
    private ItemScraperArvutitark itemScraperArvutitark;

    @Inject
    private ItemScraperGeneric itemScraperGeneric;

    @Inject
    @InjectMocks
    private ScraperService scraperService;

    @Test
    public void testSelect1AScraper() {
        String url = "https://www.1a.ee/some-product";
        scraperService.scrape(url);
        verify(itemScraper1A, times(1)).scrapeUrl(eq(url));
    }

    @Test
    public void testSelectArvutitarkScraper() {
        String url = "https://arvutitark.ee/some-product";
        scraperService.scrape(url);
        verify(itemScraperArvutitark, times(1)).scrapeUrl(eq(url));
    }

    @Test
    public void testSelectGenericScraper() {
        String url = "https://www.klick.ee/some-product";
        scraperService.scrape(url);
        verify(itemScraperGeneric, times(1)).scrapeUrl(eq(url), any());
    }

    @MockBean(value = ItemScraper1A.class)
    public ItemScraper1A itemScraper1A() {
        return mock(ItemScraper1A.class);
    }

    @MockBean(value = ItemScraperArvutitark.class)
    public ItemScraperArvutitark itemScraperArvutitark() {
        return mock(ItemScraperArvutitark.class);
    }

    @MockBean(value = ItemScraperGeneric.class)
    public ItemScraperGeneric itemScraperGeneric() {
        return mock(ItemScraperGeneric.class);
    }
}
