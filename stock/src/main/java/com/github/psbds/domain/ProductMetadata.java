package com.github.psbds.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ProductMetadata")
public class ProductMetadata extends PanacheEntity {

    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "productMetadata", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductMetadataValue> values = new ArrayList<>();

    public ProductMetadata(Product product, String name) {
        this.product = product;
        this.name = name;
        this.values = new ArrayList<>();
    }
}
