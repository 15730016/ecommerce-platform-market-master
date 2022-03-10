package com.sintkit.ecommerceservice.service.product;

import com.sintkit.ecommerceservice.dto.CategoryRequest;
import com.sintkit.ecommerceservice.exception.ResourceNotFoundException;
import com.sintkit.ecommerceservice.model.Category;
import com.sintkit.ecommerceservice.repository.CategoryRepository;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.sintkit.ecommerceservice.config.AppConstants.ID;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;

    public CategoryServiceImpl(CategoryRepository categoryRepository, EntityManager entityManager) {
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
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
    public Page<Category> findAll(Pageable pageable, boolean isDeleted) {
        return categoryRepository.findAllByDeleted(pageable, isDeleted);
    }

    public Iterable<Category> findAll(boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedCategoryFilter");
        filter.setParameter("isDeleted", isDeleted);
        Iterable<Category> categories =  categoryRepository.findAll();
        session.disableFilter("deletedCategoryFilter");
        return categories;
    }

}
