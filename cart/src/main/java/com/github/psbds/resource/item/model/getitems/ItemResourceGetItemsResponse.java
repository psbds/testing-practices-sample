package com.github.psbds.resource.item.model.getitems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResourceGetItemsResponse {
    
    @JsonProperty("items")
    private List<ItemResourceGetItemsItemResponse> items;
}