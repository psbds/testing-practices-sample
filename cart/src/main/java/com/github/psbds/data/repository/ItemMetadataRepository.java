package com.github.psbds.data.repository;

import com.github.psbds.domain.item.ItemMetadata;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ItemMetadataRepository implements PanacheRepository<ItemMetadata> {
    
    public List<ItemMetadata> findByItemId(Long itemId) {
        return find("item.id", itemId).list();
    }
}
