package com.sintkit.ecommerceservice.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    private static final long serialVersionUID = 1493867573442690205L;

    @JsonBackReference
    @OneToMany(mappedBy = "product")
    private List<ProductCategory> productCategoryList;

    @JsonBackReference
    @OneToMany(mappedBy = "product")
    private List<ProductSize> productSizeList;

    @JsonBackReference
    @OneToMany(mappedBy = "product")
    private List<ProductColor> productColorList;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private float price;

    @Column(name = "slug")
    private String slug;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_images")
    private String productImageDBs;

    @Column(name = "product_quantity")
    private int productQuantity;

    @Column(name = "product_created_date", nullable = false, updatable = false)
    protected long productCreatedDate;

    @Column(name = "product_update_date", nullable = false, updatable = false)
    protected long productUpdateDate;
}
