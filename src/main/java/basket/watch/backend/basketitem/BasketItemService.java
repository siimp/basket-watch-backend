package basket.watch.backend.basketitem;

import basket.watch.backend.basket.Basket;
import basket.watch.backend.basket.BasketRepository;
import basket.watch.backend.basket.BasketService;
import basket.watch.backend.item.ItemService;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
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

    private final EntityManager entityManager;

    public void deleteBasketItem(UUID basketUuid, Long id) {
        basketItemRepository.deleteBasketItem(basketUuid, id);
        basketRepository.findByUuid(basketUuid).ifPresent(this::updateBasket);
    }

    public BasketItem saveBasketItem(UUID basketUuid, BasketItemForm basketItemForm) {
        Optional<Basket> basketOptional = basketRepository.findByUuid(basketUuid);
        BasketItem basketItem = null;
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            basketItem = BasketItemCreator.create(basketItemForm, basket, itemService);
            basketItem = basketItemRepository.save(basketItem);
            updateBasket(basket);
        }
        return basketItem;
    }

    private void updateBasket(Basket basket) {
        entityManager.flush();
        entityManager.refresh(basket);
        basketService.updatePriceAndResetMinMax(basket);
    }
}
