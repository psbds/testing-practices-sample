package com.github.psbds.domain.item;

import com.github.psbds.errors.BusinessException;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ItemMetadata extends PanacheEntity {
    @Column(name = "[key]", nullable = false)
    public String key;

    @Column(name = "[value]", nullable = false)
    public String value;

    @ManyToOne
    @JoinColumn(name = "item_id")
    public Item item;

    // Empty constructor required by JPA/Panache - protected to prevent accidental use
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
        this.key = key;
        this.value = value;
        this.item = item;
    }
}
