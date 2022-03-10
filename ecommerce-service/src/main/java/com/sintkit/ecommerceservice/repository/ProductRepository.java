package com.sintkit.ecommerceservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sintkit.ecommerceservice.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
