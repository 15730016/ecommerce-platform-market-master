package com.sintkit.ecommerceservice.repository;

import com.sintkit.ecommerceservice.model.ImageDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDBRepository extends JpaRepository<ImageDB, Long> {

    ImageDB findImageDBByName(String name);

    ImageDB findImageDBById(Long id);

}
