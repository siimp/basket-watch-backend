package basket.watch.backend.basketitem;

import io.micronaut.validation.Validated;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Validated
@Data
public class BasketItemForm {

    @NotBlank
    private String url;

    @Min(1)
    private int quantity;
}
