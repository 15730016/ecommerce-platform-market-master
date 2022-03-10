package com.sintkit.ecommerceservice.repository;

import com.sintkit.ecommerceservice.model.Size;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends PagingAndSortingRepository<Size, Long> {

}
