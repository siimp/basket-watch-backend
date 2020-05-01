package basket.watch.backend.basket;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> findByUuid(UUID uuid);

    int deleteAllByCreatedAtLessThan(ZonedDateTime zonedDateTime);

}
