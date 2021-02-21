package basket.watch.backend.notification;

import basket.watch.backend.basket.Basket;
import basket.watch.backend.basket.BasketRepository;
import basket.watch.backend.common.entity.Notification;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.UUID;

@Singleton
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final BasketRepository basketRepository;

    public void save(UUID basketUuid, Notification notification) {
        Basket basket = basketRepository.findById(basketUuid).get();
        basket.setNotification(notification);
        basketRepository.save(basket);
    }

    public void unsubscribe(UUID basketUuid) {
        Basket basket = basketRepository.findById(basketUuid).get();
        if (basket.getNotification() != null) {
            basket.getNotification().setSubscribed(Boolean.FALSE);
            basketRepository.save(basket);
        }
    }
}
