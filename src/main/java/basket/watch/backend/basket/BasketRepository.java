package basket.watch.backend.basket;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.UUID;


@Repository
public interface BasketRepository extends JpaRepository<Basket, UUID> {

    int deleteAllByCreatedAtLessThan(ZonedDateTime zonedDateTime);

}
