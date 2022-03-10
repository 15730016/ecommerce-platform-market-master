package com.sintkit.ecommerceservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "category_image")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE category_image SET deleted = true WHERE id=?")
@FilterDef(name = "deletedCategoryImageDBFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedCategoryImageDBFilter", condition = "deleted = :isDeleted")
public class CategoryImageDB extends BaseEntity{

    private static final long serialVersionUID = -6274609486977597497L;

    private String name;

    private String type;

    @Lob
    private byte[] imageDB;

    @JsonBackReference
    @OneToOne(mappedBy = "categoryImageDB")
    private Category category;

    private boolean deleted = Boolean.FALSE;
}
