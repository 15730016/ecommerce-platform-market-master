package com.sintkit.ecommerceservice.repository;

import com.sintkit.ecommerceservice.model.Category;
import com.sintkit.ecommerceservice.model.CategoryImageDB;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

}
