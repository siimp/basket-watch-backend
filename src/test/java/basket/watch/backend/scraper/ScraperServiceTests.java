package basket.watch.backend.scraper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;


@MicronautTest
public class ScraperServiceTests {

    @Inject
    private ItemScraperGeneric itemScraperGeneric;

    @Inject
    @InjectMocks
    private ScraperService scraperService;

    @Test
    public void testSelect1AScraper() {
        String url = "https://www.1a.ee/some-product";
        scraperService.scrape(url);
        verify(itemScraperGeneric, times(1)).scrapeUrl(eq(url), any());
    }

    @Test
    public void testSelectArvutitarkScraper() {
        String url = "https://arvutitark.ee/some-product";
        scraperService.scrape(url);
        verify(itemScraperGeneric, times(1)).scrapeUrl(eq(url), any());
    }

    @Test
    public void testSelectGenericScraper() {
        String url = "https://www.klick.ee/some-product";
        scraperService.scrape(url);
        verify(itemScraperGeneric, times(1)).scrapeUrl(eq(url), any());
    }

    @MockBean(value = ItemScraperGeneric.class)
    public ItemScraperGeneric itemScraperGeneric() {
        return mock(ItemScraperGeneric.class);
    }
}
