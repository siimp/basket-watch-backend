package basket.watch.backend.basketitem;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface BasketItemRepository extends CrudRepository<BasketItem, Long> {

    @Query("DELETE FROM BasketItem b WHERE b.id = :id AND b.basket.uuid = :basketUuid")
    int deleteBasketItem(UUID basketUuid, Long id);
}
