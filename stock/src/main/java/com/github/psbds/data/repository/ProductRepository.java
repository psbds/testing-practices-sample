package com.github.psbds.data.repository;

import com.github.psbds.data.repository.dao.ProductRepositoryDAO;
import com.github.psbds.domain.Product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductRepository {

    @Inject
    ProductRepositoryDAO productRepositoryDAO;

    public void persist(Product product) {
        productRepositoryDAO.persist(product);
    }

    public Product get(Long id) {
        return productRepositoryDAO.findById(id);
    }
}
