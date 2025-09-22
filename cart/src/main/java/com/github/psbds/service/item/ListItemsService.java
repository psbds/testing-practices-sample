package com.github.psbds.service.item;

import com.github.psbds.data.repository.ItemRepository;
import com.github.psbds.domain.item.Item;
import com.github.psbds.mapper.item.GetItemResponseMapper;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsItemResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsResponse;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ListItemsService {

    private ItemRepository itemRepository;
    private GetItemResponseMapper getItemResponseMapper;

    // No-args constructor for CDI
    public ListItemsService() {
    }

    @Inject
    public ListItemsService(ItemRepository itemRepository, GetItemResponseMapper getItemResponseMapper) {
        this.itemRepository = itemRepository;
        this.getItemResponseMapper = getItemResponseMapper;
    }

    @CacheResult(cacheName = "user-items")
    public ItemResourceGetItemsResponse listByUserId(String userId) {
        // The UserId is an input
        // The List of items returned by the repository is an input
        List<Item> items = itemRepository.findByUserId(userId);

        List<ItemResourceGetItemsItemResponse> itemResponses = items.stream().map(getItemResponseMapper::map).collect(Collectors.toList());

        return new ItemResourceGetItemsResponse(itemResponses);
    }
}