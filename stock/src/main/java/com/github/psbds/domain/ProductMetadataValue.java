package com.github.psbds.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ProductMetadataValue")
public class ProductMetadataValue extends PanacheEntity {

    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_metadata_id", nullable = false)
    private ProductMetadata productMetadata;

    @Column(name = "value", nullable = false)
    private String value;

    public ProductMetadataValue(ProductMetadata productMetadata, String value) {
        this.productMetadata = productMetadata;
        this.value = value;
    }
}
