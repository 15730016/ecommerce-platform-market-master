package com.sintkit.ecommerceservice.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ColorRequest {

    @NonNull
    private String color;
}
