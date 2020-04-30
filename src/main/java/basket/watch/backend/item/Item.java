package basket.watch.backend.item;

import basket.watch.backend.common.entity.PriceHistory;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"url"}, name = "uc_item_url")
)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemType type;
    */

    @URL
    @NotBlank
    @NotNull
    private String url;

    @NotBlank
    @NotNull
    private String name;

    @Embedded
    private PriceHistory priceHistory;
}
