package com.github.psbds.resource.product.model.getproduct;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProductResourceGetProductMetadataResponse {
    
    @NotNull
    @JsonProperty("name")
    private String name;
    
    @NotNull
    @JsonProperty("values")
    private List<String> values;
    
}