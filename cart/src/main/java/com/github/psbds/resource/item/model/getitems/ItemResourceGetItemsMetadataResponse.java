package com.github.psbds.resource.item.model.getitems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResourceGetItemsMetadataResponse {
    
    @JsonProperty("key")
    private String key;
    
    @JsonProperty("value")
    private String value;
}