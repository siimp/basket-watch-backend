package basket.watch.backend.item;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Context
@ConfigurationProperties("basket-watch.job.item-price-update")
@Data
public class ItemPriceUpdateJobProperties {

    @NotBlank
    private String cron;
}
