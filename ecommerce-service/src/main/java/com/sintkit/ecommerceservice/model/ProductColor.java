package com.sintkit.ecommerceservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_color")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductColor extends BaseEntity {

    private static final long serialVersionUID = 5091771195822769389L;

    @ManyToOne
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
