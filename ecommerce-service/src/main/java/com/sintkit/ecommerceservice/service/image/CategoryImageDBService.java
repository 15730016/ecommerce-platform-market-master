package com.sintkit.ecommerceservice.service.image;

import com.sintkit.ecommerceservice.model.Category;
import com.sintkit.ecommerceservice.model.CategoryImageDB;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.stream.Stream;

public interface CategoryImageDBService {

    Optional<CategoryImageDB> findById(Long id);

    Optional<CategoryImageDB> findImageIdById(Category id);

    CategoryImageDB store(MultipartFile multipartFile);

    void update(Long id, MultipartFile multipartFile);

    CategoryImageDB getImageDB(String name);

    Stream<CategoryImageDB> getAllImageDB();
}
