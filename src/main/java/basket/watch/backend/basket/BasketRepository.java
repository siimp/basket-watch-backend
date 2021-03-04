package basket.watch.backend.basket;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface BasketRepository extends JpaRepository<Basket, UUID> {

    @Query("select b from Basket b where b.notification.subscribed = :subscribed and " +
            "b.notification.shouldSendNotification = :shouldSendNotification and " +
            "b.notification.shouldSendNotificationAt = :shouldSendNotificationAt")
    List<Basket> findAllForNotification(
            Boolean subscribed, Boolean shouldSendNotification, LocalDate shouldSendNotificationAt);


    @Query("update Basket b set b.lastViewedAt = :lastViewedAt where b.uuid = :uuid")
    void updateLastViewedAtByUuid(LocalDate lastViewedAt, UUID uuid);

    int deleteAllByLastViewedAtLessThan(LocalDate localDate);
}
