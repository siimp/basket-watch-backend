package basket.watch.backend.basket;

import basket.watch.backend.basketitem.BasketItem;
import basket.watch.backend.common.entity.PriceHistory;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Embedded
    private PriceHistory priceHistory;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "basket")
    private List<BasketItem> basketItems;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createdAt = ZonedDateTime.now();


}
