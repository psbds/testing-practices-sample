package com.github.psbds.domain.item;

import com.github.psbds.errors.exception.BusinessException;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ItemMetadata extends PanacheEntity {

    private Long id;

    @Column(name = "[key]", nullable = false)
    private String key;

    @Column(name = "[value]", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // Empty constructor required by JPA/Panache - protected to prevent accidental
    // use
    protected ItemMetadata() {
        super();
    }

    public ItemMetadata(String key, String value, Item item) {
        super();
        if (key == null || key.trim().isEmpty()) {
            throw new BusinessException("INVALID_VALUE", "Key cannot be null or empty");
        }
        if (value == null || value.trim().isEmpty()) {
            throw new BusinessException("INVALID_VALUE", "Value cannot be null or empty");
        }
        if (item == null) {
            throw new BusinessException("INVALID_VALUE", "Item cannot be null");
        }
        this.key = key;
        this.value = value;
        this.item = item;
    }
}
