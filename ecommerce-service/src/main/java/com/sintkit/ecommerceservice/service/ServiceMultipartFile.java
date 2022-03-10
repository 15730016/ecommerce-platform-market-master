package com.sintkit.ecommerceservice.service;

import com.sintkit.ecommerceservice.model.CategoryImageDB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ServiceMultipartFile<T, R> {

	T save(R r) throws IOException;

	void update(Long id, R r);

	void deleteById(Long id);

	Optional<T> findById(Long id);

	Page<T> findAll(Pageable pageable);
}
