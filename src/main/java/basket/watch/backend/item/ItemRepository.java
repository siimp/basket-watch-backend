package basket.watch.backend.item;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> findByUrl(String url);
}
