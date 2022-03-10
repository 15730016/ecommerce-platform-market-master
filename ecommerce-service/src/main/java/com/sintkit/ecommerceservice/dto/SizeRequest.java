package com.sintkit.ecommerceservice.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class SizeRequest {

    @NonNull
    private String size;
}
