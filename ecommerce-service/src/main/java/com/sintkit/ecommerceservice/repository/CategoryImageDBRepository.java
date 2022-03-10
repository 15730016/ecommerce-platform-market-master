package com.sintkit.ecommerceservice.repository;

import com.sintkit.ecommerceservice.model.Category;
import com.sintkit.ecommerceservice.model.CategoryImageDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryImageDBRepository extends JpaRepository<CategoryImageDB, Long> {

    CategoryImageDB findCategoryImageDBByName(String name);

    Optional<CategoryImageDB> findImageIdById(Category id);
}
