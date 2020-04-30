package basket.watch.backend.basketitem;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.lang.invoke.MethodHandles;
import java.util.UUID;

@Validated
@Controller("/basket/{basketUuid}/basket-item")
@RequiredArgsConstructor
public class BasketItemController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final BasketItemService basketItemService;

    @Post
    public BasketItem post(@NotNull final UUID basketUuid, final BasketItemForm basketItemForm) {
        return basketItemService.saveBasketItem(basketUuid, basketItemForm);
    }

    @Delete("/{id}")
    public void delete(@NotNull UUID basketUuid, @NotNull final Long id) {
        LOG.info("deleting basket item {} from basket {}", id, basketUuid);
        basketItemService.deleteBasketItem(basketUuid, id);
    }
}
