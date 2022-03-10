package com.sintkit.ecommerceservice.dto;

import com.sintkit.ecommerceservice.model.CategoryImageDB;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;

@Data
@Builder
@Getter
@Setter
public class CategoryRequest {

    private String categoryName;
    private String imageName;
    @OneToOne
    private CategoryImageDB categoryImageDB;
}
