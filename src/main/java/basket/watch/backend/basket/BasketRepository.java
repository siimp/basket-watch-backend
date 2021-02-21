package basket.watch.backend.basket;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface BasketRepository extends JpaRepository<Basket, UUID> {

    int deleteAllByUpdatedAtLessThan(LocalDateTime localDateTime);

    List<Basket> findAllByNotificationSubscribedAndNotificationShouldSendNotificationAndNotificationShouldSendNotificationAt(
            Boolean subscribed, Boolean shouldSendNotification, LocalDate shouldSendNotificationAt);
}
