package com.github.psbds.data.repository;

import com.github.psbds.data.repository.dao.ItemRepositoryDAO;
import com.github.psbds.domain.item.Item;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ItemRepository {

    @Inject
    ItemRepositoryDAO baseRepository;

    public void persist(Item entity) {
        baseRepository.persist(entity);
    }

    public List<Item> findByUserId(String userId) {
        return baseRepository.find("userId", userId).list();
    }
}
