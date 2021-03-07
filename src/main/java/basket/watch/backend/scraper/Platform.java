package basket.watch.backend.scraper;

import lombok.Data;

@Data
public class Platform {
    private String name;
    private String domain;
    private String scraperClass;
    private String nameJsoupSelector;
    private String nameJsoupAttributeKey;
    private String priceJsoupSelector;
    private String priceJsoupAttributeKey;
    private String image;
}
