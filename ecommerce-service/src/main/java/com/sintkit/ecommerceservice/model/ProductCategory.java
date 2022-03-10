package com.sintkit.ecommerceservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_category")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductCategory extends BaseEntity {

    private static final long serialVersionUID = -4865000569249883463L;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
