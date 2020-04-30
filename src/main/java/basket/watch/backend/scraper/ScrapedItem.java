package basket.watch.backend.scraper;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class ScrapedItem {
    private String name;
    private BigDecimal price;
    private ZonedDateTime zonedDateTime = ZonedDateTime.now();
}
