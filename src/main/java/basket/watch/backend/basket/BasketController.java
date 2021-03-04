package basket.watch.backend.basket;

import basket.watch.backend.basket.dto.BasketDto;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Validated
@Controller("/basket")
@RequiredArgsConstructor
@Slf4j
public class BasketController {

    private final BasketService basketService;

    private final BasketRepository basketRepository;

    @Post
    public BasketDto post() {
        log.info("creating new basket");
        Basket basket = BasketCreator.createEmpty();
        return basketService.save(basket);
    }

    @Get("/{uuid}")
    public Optional<BasketDto> get(@NotNull final UUID uuid) {
        log.info("getting basket {}", uuid.toString());
        Optional<BasketDto>  basket =  basketService.findByUuid(uuid);
        if (basket.isPresent()) {
            basketRepository.updateLastViewedAtByUuid(LocalDate.now(), basket.get().getUuid());
        }
        return basket;
    }

    @Delete("/{uuid}")
    public void delete(@NotNull final UUID uuid) {
        log.info("deleting basket {}", uuid.toString());
        basketRepository.deleteById(uuid);
    }

}
