package com.sintkit.ecommerceservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "image")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ImageDB extends BaseEntity {

    private static final long serialVersionUID = 3929576207167480447L;

    private String name;

    private String type;

    @Lob
    private byte[] imageDB;

    @JsonBackReference
    @OneToMany(mappedBy = "imageDB")
    private List<CategoryImageDB> categoryImageDBList;
}
