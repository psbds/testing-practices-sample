package com.github.psbds.factory.item;

import com.github.psbds.domain.item.Item;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemFactory {

    public Item create(String userId, ItemResourcePostItemRequest itemRequest) {
        var item = new Item(
                userId,
                itemRequest.getProductId(),
                itemRequest.getQuantity(),
                itemRequest.getPrice());

        for (var metadataRequest : itemRequest.getMetadata()) {
            item.addMetadata(metadataRequest.getKey(), metadataRequest.getValue());
        }

        return item;
    }
}
