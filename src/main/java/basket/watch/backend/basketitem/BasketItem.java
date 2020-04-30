package basket.watch.backend.basketitem;

import basket.watch.backend.basket.Basket;
import basket.watch.backend.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(0)
    private int quantity = 0;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_basket_item_item_id"))
    private Item item;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_basket_item_basket_id"))
    private Basket basket;



}
