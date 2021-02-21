package basket.watch.backend.basket.dto;

import basket.watch.backend.basket.Basket;
import basket.watch.backend.basketitem.BasketItem;
import basket.watch.backend.common.entity.Notification;
import basket.watch.backend.common.entity.PriceHistory;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class BasketDto {

    private UUID uuid;

    private PriceHistory priceHistory;

    private List<BasketItem> basketItems;

    private LocalDate willBeDeletedAt;

    private Notification notification;

    public static BasketDto of(Basket basket, Integer basketValidityInWeeks) {
        BasketDto dto = new BasketDto();
        dto.setUuid(basket.getUuid());
        dto.setPriceHistory(basket.getPriceHistory());
        dto.setBasketItems(basket.getBasketItems());
        dto.setWillBeDeletedAt(basket.getUpdatedAt().plusWeeks(basketValidityInWeeks).toLocalDate());
        dto.setNotification(basket.getNotification());
        return dto;
    }
}
