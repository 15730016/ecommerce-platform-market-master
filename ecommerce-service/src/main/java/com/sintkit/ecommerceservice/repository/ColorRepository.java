package com.sintkit.ecommerceservice.repository;

import com.sintkit.ecommerceservice.model.Color;
import com.sintkit.ecommerceservice.model.ProductColor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends PagingAndSortingRepository<Color, Long> {

}
