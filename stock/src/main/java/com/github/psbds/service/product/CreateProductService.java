package com.github.psbds.service.product;

import com.github.psbds.data.repository.ProductRepository;
import com.github.psbds.domain.Product;
import com.github.psbds.domain.ProductMetadata;
import com.github.psbds.domain.ProductMetadataValue;
import com.github.psbds.resource.product.model.postproduct.ProductResourcePostProductRequest;
import com.github.psbds.resource.product.model.postproduct.ProductResourcePostProductResponse;
import com.github.psbds.resource.product.model.postproduct.ProductResourcePostProductMetadataRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateProductService {

    @Inject
    ProductRepository productRepository;

    @Transactional
    public ProductResourcePostProductResponse create(ProductResourcePostProductRequest request) {
        // Create the product entity
        Product product = new Product(request.getName(), request.getStock(), request.getPrice());

        // Create metadata and their values
        if (request.getMetadata() != null) {
            for (ProductResourcePostProductMetadataRequest metadataRequest : request.getMetadata()) {
                ProductMetadata metadata = new ProductMetadata(product, metadataRequest.getName());

                if (metadataRequest.getValues() != null) {
                    for (String value : metadataRequest.getValues()) {
                        ProductMetadataValue metadataValue = new ProductMetadataValue(metadata, value);
                        metadata.getValues().add(metadataValue);
                    }
                }

                product.getMetadata().add(metadata);
            }
        }

        // Persist the product (cascade will persist metadata and values)
        productRepository.persist(product);

        // Map to response
        return new ProductResourcePostProductResponse(product.getId());
    }

}