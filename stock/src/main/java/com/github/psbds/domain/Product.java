package com.github.psbds.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Product")
public class Product extends PanacheEntity {

    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductMetadata> metadata = new ArrayList<>();

    public Product(String name, Integer stock, BigDecimal price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.metadata = new ArrayList<>();
    }
}
