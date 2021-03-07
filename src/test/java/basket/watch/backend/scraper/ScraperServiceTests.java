package basket.watch.backend.scraper;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@MicronautTest
public class ScraperServiceTests {

    @Spy
    private ItemScraper1A itemScraper1A;

    @Inject
    @InjectMocks
    private ScraperService scraperService;


    @Test
    public void testSelect1AScraper() {
        String url = "https://www.1a.ee/some-product";
        scraperService.scrape(url);
        verify(itemScraper1A, times(1)).scrapeUrl(eq(url));
    }
}
