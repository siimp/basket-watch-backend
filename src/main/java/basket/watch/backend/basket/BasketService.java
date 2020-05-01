package basket.watch.backend.basket;

import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Singleton
@RequiredArgsConstructor
public class BasketService {

    public Basket updatePriceAndResetMinMax(Basket basket) {
        updatePrice(basket);
        resetMinMaxTo(basket, basket.getPriceHistory().getPrice(), basket.getPriceHistory().getPriceAt());
        return basket;
    }

    public Basket updatePrice(Basket basket) {
        BigDecimal newPrice = calculatePrice(basket);
        ZonedDateTime newPriceAt = ZonedDateTime.now();
        basket.getPriceHistory().setPrice(newPrice);
        basket.getPriceHistory().setPriceAt(newPriceAt);

        if (newPrice.compareTo(basket.getPriceHistory().getPriceMin()) < 0) {
            basket.getPriceHistory().setPriceMin(newPrice);
            basket.getPriceHistory().setPriceMinAt(newPriceAt);
        } else if (newPrice.compareTo(basket.getPriceHistory().getPriceMax()) > 0) {
            basket.getPriceHistory().setPriceMax(newPrice);
            basket.getPriceHistory().setPriceMaxAt(newPriceAt);
        }

        return basket;

    }

    private void resetMinMaxTo(Basket basket, BigDecimal price, ZonedDateTime priceAt) {
        basket.getPriceHistory().setPriceMax(price);
        basket.getPriceHistory().setPriceMaxAt(priceAt);
        basket.getPriceHistory().setPriceMin(price);
        basket.getPriceHistory().setPriceMinAt(priceAt);
    }

    private BigDecimal calculatePrice(Basket basket) {
        return basket.getBasketItems().stream()
                .map(bi -> bi.getItem().getPriceHistory().getPrice().multiply(BigDecimal.valueOf(bi.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
