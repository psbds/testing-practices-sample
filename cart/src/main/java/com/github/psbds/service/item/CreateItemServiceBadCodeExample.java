package com.github.psbds.service.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.psbds.backends.stock.StockAPIClientWrapper;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductResponse;
import com.github.psbds.data.repository.ItemMetadataRepository;
import com.github.psbds.data.repository.ItemRepository;
import com.github.psbds.domain.item.Item;
import com.github.psbds.errors.ErrorCodes;
import com.github.psbds.errors.exception.BusinessException;
import com.github.psbds.errors.exception.BusinessExceptionError;
import com.github.psbds.factory.item.ItemFactory;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;
import com.github.psbds.service.product.ValidateCartProductService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CreateItemServiceBadCodeExample {

    private ItemRepository itemRepository;
    private ItemMetadataRepository itemMetadataRepository;
    private StockAPIClientWrapper stockApiClient;

    @Inject
    public CreateItemServiceBadCodeExample(ItemRepository itemRepository, ItemMetadataRepository itemMetadataRepository, StockAPIClientWrapper stockApiClient) {
        super();
        this.itemRepository = itemRepository;
        this.itemMetadataRepository = itemMetadataRepository;
        this.stockApiClient = stockApiClient;
    }

    public ItemResourcePostItemResponse create(String userId, ItemResourcePostItemRequest request) {

        var metadataMap = request.getMetadata().stream().collect(Collectors.toMap(metadataItem -> metadataItem.getKey(), metadataItem -> metadataItem.getValue()));

        var product = stockApiClient.getProduct(request.getProductId());

        if (product.getStock() < request.getQuantity()) {
            throw new BusinessException(ErrorCodes.INSUFFICIENT_STOCK, "Insufficient items in Stock");
        }

        if (product.getPrice().compareTo(request.getPrice()) != 0) {
            throw new BusinessException(ErrorCodes.INVALID_PRICE, "Product price does not match current price");
        }

        // Converting to Map<String, HashSet<String>> to make easier to run the validations
        Map<String, HashSet<String>> productMetadataMap = product.getMetadata().stream()
                .collect(Collectors.toMap(metadataItem -> metadataItem.getName(), metadataItem -> new HashSet<>(metadataItem.getValues())));

        List<BusinessExceptionError> errors = new ArrayList<BusinessExceptionError>();

        for (var data : metadataMap.entrySet()) {
            var productMetadata = productMetadataMap.get(data.getKey());
            if (productMetadata == null) {
                errors.add(new BusinessExceptionError(String.format("Invalid Metadata %s", data.getKey())));
                continue;
            }
            if (!productMetadata.contains(data.getValue())) {
                errors.add(new BusinessExceptionError(data.getKey(), String.format("Invalid Metadata Value %s", data.getValue())));
            }
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(ErrorCodes.INVALID_ITEM_METADATA, "Invalid item metadata", errors);
        }

        var item = new Item(userId, request.getProductId(), request.getQuantity(), request.getPrice());

        for (var metadataRequest : request.getMetadata()) {
            item.addMetadata(metadataRequest.getKey(), metadataRequest.getValue());
        }

        this.itemRepository.persist(item);

        this.itemMetadataRepository.persist(item.getMetadata());

        return new ItemResourcePostItemResponse(item.getId());
    }
}