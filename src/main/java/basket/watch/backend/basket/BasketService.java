package basket.watch.backend.basket;

import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Singleton
@RequiredArgsConstructor
public class BasketService {

    public Basket updatePriceAndClearMinMax(Basket basket) {
        basket.getPriceHistory().setPrice(calculatePrice(basket));
        basket.getPriceHistory().setPriceAt(ZonedDateTime.now());
        basket.getPriceHistory().setPriceMax(basket.getPriceHistory().getPrice());
        basket.getPriceHistory().setPriceMaxAt(basket.getPriceHistory().getPriceAt());
        basket.getPriceHistory().setPriceMin(basket.getPriceHistory().getPrice());
        basket.getPriceHistory().setPriceMinAt(basket.getPriceHistory().getPriceAt());

        return basket;
    }

    private BigDecimal calculatePrice(Basket basket) {
        return basket.getBasketItems().stream()
                .map(bi -> bi.getItem().getPriceHistory().getPrice().multiply(BigDecimal.valueOf(bi.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
