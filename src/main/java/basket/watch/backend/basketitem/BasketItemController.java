package basket.watch.backend.basketitem;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@Validated
@Controller("/basket/{basketUuid}/basket-item")
@RequiredArgsConstructor
public class BasketItemController {

    private final BasketItemService basketItemService;

    @Post
    public BasketItem post(@NotNull final UUID basketUuid, final BasketItemForm basketItemForm) {
        log.info("adding item to basket {}", basketUuid);
        return basketItemService.saveBasketItem(basketUuid, basketItemForm);
    }

    @Delete("/{id}")
    public void delete(@NotNull UUID basketUuid, @NotNull final Long id) {
        log.info("deleting basket item {} from basket {}", id, basketUuid);
        basketItemService.deleteBasketItem(basketUuid, id);
    }
}
