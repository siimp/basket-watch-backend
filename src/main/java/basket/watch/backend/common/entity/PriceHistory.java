package basket.watch.backend.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Embeddable
@Data
public class PriceHistory {

    @Min(0)
    private BigDecimal price;

    @JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime priceAt;

    @Min(0)
    private BigDecimal priceMin;

    @JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime priceMinAt;

    @Min(0)
    private BigDecimal priceMax;

    @JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime priceMaxAt;
}
