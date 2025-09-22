package com.github.psbds.data.repository.dao;

import com.github.psbds.domain.Product;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepositoryDAO implements PanacheRepository<Product> {

}
