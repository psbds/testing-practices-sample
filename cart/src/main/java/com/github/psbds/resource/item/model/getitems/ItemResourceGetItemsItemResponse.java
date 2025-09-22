package com.github.psbds.resource.item.model.getitems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResourceGetItemsItemResponse {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("product_id")
    private Long productId;
    
    @JsonProperty("quantity")
    private Integer quantity;
    
    @JsonProperty("price")
    private BigDecimal price;
    
    @JsonProperty("metadata")
    private List<ItemResourceGetItemsMetadataResponse> metadata;
}