package com.github.psbds.backends.stock.model.getproduct;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StockAPIGetProductMetadataResponse {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("values")
    private List<String> values = new ArrayList<>();
}