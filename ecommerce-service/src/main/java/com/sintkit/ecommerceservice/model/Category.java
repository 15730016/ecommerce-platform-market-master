package com.sintkit.ecommerceservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity{

    private static final long serialVersionUID = 3837851602714898259L;

    private String categoryName;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private CategoryImageDB categoryImageDB;

    @JsonBackReference
    @OneToMany(mappedBy = "category")
    private List<ProductCategory> productCategoryList;
}
