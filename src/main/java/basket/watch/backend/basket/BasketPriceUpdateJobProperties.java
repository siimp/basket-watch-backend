package basket.watch.backend.basket;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Context
@ConfigurationProperties("basket-watch.job.basket-price-update")
@Data
public class BasketPriceUpdateJobProperties {

    @NotBlank
    private String cron;
}
