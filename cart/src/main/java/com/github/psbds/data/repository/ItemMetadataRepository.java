package com.github.psbds.data.repository;

import com.github.psbds.data.repository.dao.ItemMetadataRepositoryDAO;
import com.github.psbds.domain.item.ItemMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ItemMetadataRepository {
    
    @Inject
    ItemMetadataRepositoryDAO baseRepository;
    
    public void persist(Iterable<ItemMetadata> entities) {
        baseRepository.persist(entities);
    }
}
