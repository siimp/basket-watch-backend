package basket.watch.backend.basket;

import basket.watch.backend.basketitem.BasketItem;
import basket.watch.backend.common.entity.AuditedEntity;
import basket.watch.backend.common.entity.Notification;
import basket.watch.backend.common.entity.PriceHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(indexes = {
        @Index(name = "idx_created_at", columnList = "createdAt")
})
@Entity
public class Basket extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Embedded
    private PriceHistory priceHistory;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "basket")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<BasketItem> basketItems;

    @Embedded
    private Notification notification;

    private LocalDate lastViewedAt;

}
