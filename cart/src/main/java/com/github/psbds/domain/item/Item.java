package com.github.psbds.domain.item;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.github.psbds.errors.exception.BusinessException;

@Entity
@Getter
public class Item extends PanacheEntity {

    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "item")
    private List<ItemMetadata> metadata = new ArrayList<ItemMetadata>();

    // Empty constructor required by JPA/Panache - protected to prevent accidental
    // use
    protected Item() {
        super();
    }

    public Item(String userId, Long productId, int quantity, BigDecimal price) {
        super();

        if (userId == null || userId.trim().isEmpty()) {
            throw new BusinessException("INVALID_VALUE", "User ID is required and cannot be empty");
        }
        if (quantity <= 0) {
            throw new BusinessException("INVALID_VALUE", "Quantity should be greater than zero");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("INVALID_VALUE", "Price should not be null or negative");
        }
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public void addMetadata(String key, String value) {
        ItemMetadata metadata = new ItemMetadata(key, value, this);
        this.metadata.add(metadata);
    }
}
