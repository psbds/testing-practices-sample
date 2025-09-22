package com.github.psbds.backends.stock.model.getproduct;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StockAPIGetProductResponse {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("stock")
    private Integer stock;
    
    @JsonProperty("price")
    private BigDecimal price;
    
    @JsonProperty("metadata")
    private List<StockAPIGetProductMetadataResponse> metadata = new ArrayList<>();
}
