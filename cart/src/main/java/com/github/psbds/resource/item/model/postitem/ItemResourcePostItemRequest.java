package com.github.psbds.resource.item.model.postitem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ItemResourcePostItemRequest {
    
    @JsonProperty("product_id")
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    @JsonProperty("quantity")
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    
    @JsonProperty("price")
    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be positive or zero")
    private BigDecimal price;
    
    @JsonProperty("metadata")
    @NotNull(message = "Metadata is required")
    private List<ItemResourcePostItemMetadataRequest> metadata;
}
