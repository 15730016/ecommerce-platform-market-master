package com.sintkit.ecommerceservice.service.product;

import com.sintkit.ecommerceservice.dto.CategoryRequest;
import com.sintkit.ecommerceservice.exception.ResourceNotFoundException;
import com.sintkit.ecommerceservice.model.Category;
import com.sintkit.ecommerceservice.model.CategoryImageDB;
import com.sintkit.ecommerceservice.model.ImageDB;
import com.sintkit.ecommerceservice.repository.CategoryRepository;
import com.sintkit.ecommerceservice.repository.ImageDBRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.sintkit.ecommerceservice.config.AppConstants.ID;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final ImageDBRepository imageRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ImageDBRepository imageRepository) {
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Category save(CategoryRequest categoryRequest) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryRequest, category);
        return categoryRepository.save(category);
    }

    @Override
    public void update(Long id, CategoryRequest categoryRequest) {
        findById(id).map(category -> {
            BeanUtils.copyProperties(categoryRequest, category);
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("Category", ID, id));
    }


    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

}
