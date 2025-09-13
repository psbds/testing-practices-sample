package com.github.psbds.resource.item.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class PostItemMetadataRequest {
    
    @JsonProperty("key")
    @NotNull(message = "Key is required")
    @NotBlank(message = "Key cannot be blank")
    @Size(min = 1, max = 255, message = "Key must be between 1 and 255 characters")
    private String key;
    
    @JsonProperty("value")
    @NotNull(message = "Value is required")
    @NotBlank(message = "Value cannot be blank")
    @Size(min = 1, max = 255, message = "Value must be between 1 and 255 characters")
    private String value;
}
