package com.sintkit.ecommerceservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "size")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Size extends BaseEntity{

    private static final long serialVersionUID = -3224515145356712396L;

    private String size;

    @JsonBackReference
    @OneToMany(mappedBy = "size")
    private List<ProductSize> productSizeList;
}
