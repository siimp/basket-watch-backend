package basket.watch.backend.common.entity;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class AuditedEntity {

    // @NotNull
    @DateCreated
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime createdAt;

    @DateUpdated
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime updatedAt;

}
