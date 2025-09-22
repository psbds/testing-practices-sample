package com.github.psbds.service.product;

import com.github.psbds.data.repository.ProductRepository;
import com.github.psbds.domain.Product;
import com.github.psbds.domain.ProductMetadata;
import com.github.psbds.domain.ProductMetadataValue;
import com.github.psbds.errors.exception.RecordNotFoundException;
import com.github.psbds.resource.product.model.getproduct.ProductResourceGetProductMetadataResponse;
import com.github.psbds.resource.product.model.getproduct.ProductResourceGetProductResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GetProductService {

    @Inject
    ProductRepository productRepository;

    public ProductResourceGetProductResponse get(Long id) {
        Product product = productRepository.get(id);

        if (product == null) {
            throw new RecordNotFoundException("Product", id.toString());
        }

        List<ProductResourceGetProductMetadataResponse> metadataResponse = product.getMetadata().stream().map(this::mapMetadataToResponse).collect(Collectors.toList());

        return new ProductResourceGetProductResponse(product.getName(), product.getStock(), product.getPrice(), metadataResponse);
    }

    private ProductResourceGetProductMetadataResponse mapMetadataToResponse(ProductMetadata metadata) {
        List<String> values = metadata.getValues().stream().map(ProductMetadataValue::getValue).collect(Collectors.toList());

        return new ProductResourceGetProductMetadataResponse(metadata.getName(), values);
    }
}
