package com.sintkit.ecommerceservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "color")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Color extends BaseEntity{

    private static final long serialVersionUID = 5369611022966307928L;

    private String color;

    @JsonBackReference
    @OneToMany(mappedBy = "color")
    private List<ProductColor> productColorList;
}
