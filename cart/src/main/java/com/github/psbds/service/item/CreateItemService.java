package com.github.psbds.service.item;

import java.util.Map;
import java.util.stream.Collectors;

import com.github.psbds.data.repository.ItemMetadataRepository;
import com.github.psbds.data.repository.ItemRepository;
import com.github.psbds.factory.item.ItemFactory;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;
import com.github.psbds.service.product.ValidateCartProductService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

@ApplicationScoped
@NoArgsConstructor
public class CreateItemService {

    private ValidateCartProductService validateCartProductService;
    private ItemFactory itemFactory;
    private ItemRepository itemRepository;
    private ItemMetadataRepository itemMetadataRepository;

    @Inject
    public CreateItemService(ValidateCartProductService validateCartProductService, ItemRepository itemRepository, ItemFactory itemFactory, ItemMetadataRepository itemMetadataRepository) {
        super();
        this.validateCartProductService = validateCartProductService;
        this.itemRepository = itemRepository;
        this.itemFactory = itemFactory;
        this.itemMetadataRepository = itemMetadataRepository;
    }

    public ItemResourcePostItemResponse create(String userId, ItemResourcePostItemRequest request) {

        validateCartItem(request);

        var item = itemFactory.create(userId, request);

        // What if someone refactors this method and forgets to persist in the database?
        this.itemRepository.persist(item);

        this.itemMetadataRepository.persist(item.getMetadata());

        return new ItemResourcePostItemResponse(item.getId());
    }

    private void validateCartItem(ItemResourcePostItemRequest request) {
        var metadataMap = request.getMetadata().stream().collect(Collectors.toMap(metadataItem -> metadataItem.getKey(), metadataItem -> metadataItem.getValue()));

        validateCartProductService.validateCartProduct(request.getProductId(), request.getQuantity(), request.getPrice(), metadataMap);
    }
}
