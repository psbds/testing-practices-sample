package com.github.psbds.data.repository.dao;

import com.github.psbds.domain.item.Item;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemRepositoryDAO implements PanacheRepository<Item> {
    
}
