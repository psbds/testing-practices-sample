package com.github.psbds.resource.item.model.postitem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResourcePostItemResponse {
    
    @JsonProperty("id")
    private Long id;
}