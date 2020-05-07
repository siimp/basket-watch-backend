package basket.watch.backend.basket;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Validated
@Controller("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketRepository basketRepository;

    @Post
    public Basket post() {
        Basket basket = BasketCreator.createEmpty();
        return basketRepository.save(basket);
    }

    @Get("/{uuid}")
    public Optional<Basket> get(@NotNull final UUID uuid) {
        return basketRepository.findById(uuid);
    }

}
