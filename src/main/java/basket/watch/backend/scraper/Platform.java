package basket.watch.backend.scraper;

import lombok.Data;

@Data
public class Platform {
    private String name;
    private String domain;
    private String scraperClass;
    private String nameXpath;
    private String priceXpath;
    private String image;
}
