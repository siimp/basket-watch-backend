package basket.watch.backend.basket;

import basket.watch.backend.basketitem.BasketItem;
import basket.watch.backend.common.entity.PriceHistory;
import basket.watch.backend.item.Item;
import basket.watch.backend.item.ItemRepository;
import basket.watch.backend.item.ItemType;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class BasketCreator {

    private static Random random = new Random();

    public static Basket create(ItemRepository itemRepository) {
        Basket basket = new Basket();
        basket.setPriceHistory(getRandomPriceHistory(random));
        basket.setBasketItems(new ArrayList<>());
        basket.getBasketItems().add(generateRandomItem(itemRepository, basket));
        basket.getBasketItems().add(generateRandomItem(itemRepository, basket));
        basket.getBasketItems().add(generateRandomItem(itemRepository, basket));
        return basket;
    }

    private static BasketItem generateRandomItem(ItemRepository itemRepository, Basket basket) {

        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);

        Item item = new Item();
        // item.setType(ItemType.ARVUTITARK);
        item.setUrl("https://test." + UUID.randomUUID().toString() + ".com");
        item.setName(UUID.randomUUID().toString().substring(0, 4));
        item.setPriceHistory(getRandomPriceHistory(random));

        basketItem.setItem(itemRepository.save(item));
        return basketItem;
    }

    private static PriceHistory getRandomPriceHistory(Random random) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(BigDecimal.valueOf(Math.abs(random.nextLong() % 1000)));
        priceHistory.setPriceAt(ZonedDateTime.now());

        priceHistory.setPriceMin(BigDecimal.valueOf(Math.abs(random.nextLong() % 1000)));
        priceHistory.setPriceMinAt(ZonedDateTime.now());

        priceHistory.setPriceMax(BigDecimal.valueOf(Math.abs(random.nextLong() % 1000)));
        priceHistory.setPriceMaxAt(ZonedDateTime.now());
        return priceHistory;
    }

    public static Basket createEmpty() {
        Basket basket = new Basket();
        basket.setPriceHistory(createEmptyPriceHistory());
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
