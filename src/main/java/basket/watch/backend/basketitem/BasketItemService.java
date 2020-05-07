package basket.watch.backend.basketitem;

import basket.watch.backend.basket.Basket;
import basket.watch.backend.basket.BasketRepository;
import basket.watch.backend.basket.BasketService;
import basket.watch.backend.item.ItemService;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Singleton
@Transactional
@RequiredArgsConstructor
public class BasketItemService {

    private final BasketRepository basketRepository;

    private final BasketItemRepository basketItemRepository;

    private final ItemService itemService;

    private final BasketService basketService;

    public void deleteBasketItem(UUID basketUuid, Long id) {
        basketItemRepository.deleteBasketItem(basketUuid, id);
        basketRepository.findById(basketUuid).ifPresent(this::updateBasket);
    }

    public BasketItem saveBasketItem(UUID basketUuid, BasketItemForm basketItemForm) {
        Optional<Basket> basketOptional = basketRepository.findById(basketUuid);
        BasketItem basketItem = null;
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            basketItem = BasketItemCreator.create(basketItemForm, basket, itemService);
            basket.getBasketItems().add(basketItem);
            basketService.updatePriceAndResetMinMax(basket);
        }
        return basketItem;
    }

    private void updateBasket(Basket basket) {
        basketService.updatePriceAndResetMinMax(basket);
    }
}
