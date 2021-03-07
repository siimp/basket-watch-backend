package basket.watch.backend.basket;

import basket.watch.backend.common.entity.Notification;
import basket.watch.backend.common.entity.PriceHistory;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@UtilityClass
public class BasketCreator {

    public static Basket createEmpty() {
        Basket basket = new Basket();
        basket.setPriceHistory(createEmptyPriceHistory());
        basket.setNotification(new Notification());
        basket.setBasketItems(new ArrayList<>());
        return basket;
    }

    private static PriceHistory createEmptyPriceHistory() {
        PriceHistory priceHistory = new PriceHistory();
        ZonedDateTime now = ZonedDateTime.now();
        priceHistory.setPrice(BigDecimal.ZERO);
        priceHistory.setPriceAt(now);
        priceHistory.setPriceMin(BigDecimal.ZERO);
        priceHistory.setPriceMinAt(now);
        priceHistory.setPriceMax(BigDecimal.ZERO);
        priceHistory.setPriceMaxAt(now);
        return priceHistory;
    }

}
