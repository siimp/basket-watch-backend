package basket.watch.backend.basket;

import basket.watch.backend.basket.dto.BasketDto;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Singleton
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;

    private final BasketDeleteJobProperties basketDeleteJobProperties;

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
            setNotificationForLowerPrice(basket);
        } else if (newPrice.compareTo(basket.getPriceHistory().getPriceMax()) > 0) {
            basket.getPriceHistory().setPriceMax(newPrice);
            basket.getPriceHistory().setPriceMaxAt(newPriceAt);
        }

        return basket;

    }

    private void setNotificationForLowerPrice(Basket basket) {
        if (basket.getNotification() != null) {
            basket.getNotification().setShouldSendNotification(Boolean.TRUE);
            basket.getNotification().setShouldSendNotificationAt(LocalDate.now());
        }
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

    public Optional<BasketDto> findByUuid(UUID uuid) {
        Optional<Basket> basketOptional = basketRepository.findById(uuid);
        if (basketOptional.isPresent()) {
            return Optional.of(BasketDto.of(basketOptional.get(), basketDeleteJobProperties.getBasketValidityInWeeks()));
        }

        return Optional.empty();
    }

    public BasketDto save(Basket basket) {
        Basket savedBasket = basketRepository.save(basket);
        return BasketDto.of(savedBasket, basketDeleteJobProperties.getBasketValidityInWeeks());
    }

    public boolean existsByUuid(UUID basketUuid) {
        return basketRepository.existsById(basketUuid);
    }
}
