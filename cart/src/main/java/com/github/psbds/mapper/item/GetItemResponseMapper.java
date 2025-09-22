package com.github.psbds.mapper.item;

import java.util.List;
import java.util.stream.Collectors;

import com.github.psbds.domain.item.Item;
import com.github.psbds.domain.item.ItemMetadata;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsItemResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsMetadataResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetItemResponseMapper {
    
    public ItemResourceGetItemsItemResponse map(Item item) {
        List<ItemResourceGetItemsMetadataResponse> metadataResponses = item.getMetadata().stream()
            .map(this::mapToMetadataResponse)
            .collect(Collectors.toList());

        return new ItemResourceGetItemsItemResponse(
            item.getId(),
            item.getProductId(),
            item.getQuantity(),
            item.getPrice(),
            metadataResponses
        );
    }

    private ItemResourceGetItemsMetadataResponse mapToMetadataResponse(ItemMetadata metadata) {
        return new ItemResourceGetItemsMetadataResponse(
            metadata.getKey(),
            metadata.getValue()
        );
    }
}
