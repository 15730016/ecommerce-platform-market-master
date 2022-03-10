package com.sintkit.ecommerceservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_size")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductSize extends BaseEntity {

    private static final long serialVersionUID = -4317500266406162473L;

    @ManyToOne
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
