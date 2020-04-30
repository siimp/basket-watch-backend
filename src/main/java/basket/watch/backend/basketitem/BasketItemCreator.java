package basket.watch.backend.basketitem;

import basket.watch.backend.basket.Basket;
import basket.watch.backend.item.ItemService;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BasketItemCreator {

    public static BasketItem create(BasketItemForm basketItemForm, Basket basket, ItemService itemService) {
        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setQuantity(basketItemForm.getQuantity());
        basketItem.setItem(itemService.create(basketItemForm.getUrl()));
        return basketItem;
    }
}
