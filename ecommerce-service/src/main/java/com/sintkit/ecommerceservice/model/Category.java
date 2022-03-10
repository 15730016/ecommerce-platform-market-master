package com.sintkit.ecommerceservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE id=?")
@FilterDef(name = "deletedCategoryFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedCategoryFilter", condition = "deleted = :isDeleted")
public class Category extends BaseEntity{

    private static final long serialVersionUID = 3837851602714898259L;

    private String categoryName;

    private boolean deleted = Boolean.FALSE;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private CategoryImageDB categoryImageDB;

    @JsonBackReference
    @OneToMany(mappedBy = "category")
    private List<ProductCategory> productCategoryList;
}
