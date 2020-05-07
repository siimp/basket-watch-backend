package basket.watch.backend.basket;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Context
@ConfigurationProperties("basket-watch.job.basket-delete")
@Data
public class BasketDeleteJobProperties {

    @NotBlank
    private String cron;

    @Min(1)
    @NotNull
    private Integer basketValidityInWeeks;
}