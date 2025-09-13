package com.github.psbds.data.repository;

import com.github.psbds.domain.item.Item;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ItemRepository implements PanacheRepository<Item> {
    
    public List<Item> findByProductId(Long productId) {
        return find("productId", productId).list();
    }
}
