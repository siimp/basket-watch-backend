package basket.watch.backend.item;

import basket.watch.backend.common.entity.PriceHistory;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @URL
    @NotBlank
    @NotNull
    @Column(unique = true)
    private String url;

    @NotBlank
    @NotNull
    private String name;

    @Embedded
    private PriceHistory priceHistory;
}
