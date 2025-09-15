package com.github.psbds.service.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.psbds.backends.stock.StockAPIClientWrapper;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductResponse;
import com.github.psbds.errors.ErrorCodes;
import com.github.psbds.errors.exception.BusinessException;
import com.github.psbds.errors.exception.BusinessExceptionError;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ValidateCartProductService {

    StockAPIClientWrapper stockApiClient;

    @Inject
    public ValidateCartProductService(StockAPIClientWrapper stockApiClient) {
        this.stockApiClient = stockApiClient;
    }

    public void validateCartProduct(Long productId, int quantity, BigDecimal price, Map<String, String> metadata) {
        var product = stockApiClient.getProduct(productId);

        if (product.getStock() < quantity) {
            throw new BusinessException(ErrorCodes.INSUFFICIENT_STOCK, "Insufficient items in Stock");
        }

        if (product.getPrice().compareTo(price) != 0) {
            throw new BusinessException(ErrorCodes.INVALID_PRICE, "Product price does not match current price");
        }

        validateMedatata(product, metadata);
    }

    private void validateMedatata(StockAPIGetProductResponse product, Map<String, String> metadata) {
        // Converting to Map<String, HashSet<String>> to make easier to run the validations
        Map<String, HashSet<String>> productMetadataMap = product.getMetadata().stream()
                .collect(Collectors.toMap(metadataItem -> metadataItem.getName(), metadataItem -> new HashSet<>(metadataItem.getValues())));

        List<BusinessExceptionError> errors = new ArrayList<BusinessExceptionError>();

        for (var data : metadata.entrySet()) {
            var productMetadata = productMetadataMap.get(data.getKey());
            if (productMetadata == null) {
                errors.add(new BusinessExceptionError(String.format("Invalid Metadata %s", data.getKey())));
                continue;
            }
            if (!productMetadata.contains(data.getValue())) {
                errors.add(new BusinessExceptionError(data.getKey(),String.format("Invalid Metadata Value %s", data.getValue())));
            }
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_ITEM_METADATA, "Invalid item metadata", errors);
        }
    }
}
