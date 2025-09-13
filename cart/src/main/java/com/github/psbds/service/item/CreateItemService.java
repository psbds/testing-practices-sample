package com.github.psbds.service.item;

import com.github.psbds.data.repository.ItemMetadataRepository;
import com.github.psbds.data.repository.ItemRepository;
import com.github.psbds.factory.item.ItemFactory;
import com.github.psbds.resource.item.model.PostItemRequest;
import com.github.psbds.resource.item.model.PostItemResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CreateItemService {

    private ItemFactory itemFactory;
    private ItemRepository itemRepository;
    private ItemMetadataRepository itemMetadataRepository;

    @Inject
    public CreateItemService(ItemRepository itemRepository, ItemFactory itemFactory,
            ItemMetadataRepository itemMetadataRepository) {
        super();
        this.itemRepository = itemRepository;
        this.itemFactory = itemFactory;
        this.itemMetadataRepository = itemMetadataRepository;
    }

    public PostItemResponse create(String userId, PostItemRequest request) {
        var item = itemFactory.create(userId, request);

        this.itemRepository.persist(item);

        this.itemMetadataRepository.persist(item.metadata);

        return new PostItemResponse(item.id);
    }
}
