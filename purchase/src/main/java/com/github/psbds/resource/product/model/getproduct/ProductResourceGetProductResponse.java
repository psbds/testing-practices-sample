package com.github.psbds.resource.product.model.getproduct;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResourceGetProductResponse {
    
    @NotNull
    @JsonProperty("name")
    private String name;
    
    @NotNull
    @JsonProperty("stock")
    private Integer stock;
    
    @NotNull
    @JsonProperty("price")
    private BigDecimal price;
    
    @NotNull
    @JsonProperty("metadata")
    private List<ProductResourceGetProductMetadataResponse> metadata;
    
}