package com.sintkit.ecommerceservice.service.image;

import com.sintkit.ecommerceservice.exception.ResourceNotFoundException;
import com.sintkit.ecommerceservice.exception.StorageException;
import com.sintkit.ecommerceservice.model.Category;
import com.sintkit.ecommerceservice.model.CategoryImageDB;
import com.sintkit.ecommerceservice.repository.CategoryImageDBRepository;
import com.sintkit.ecommerceservice.util.ImageDBUtility;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.sintkit.ecommerceservice.config.AppConstants.ID;

@Service
public class CategoryImageDBServiceImpl implements CategoryImageDBService {

    private final CategoryImageDBRepository categoryImageDBRepository;
    private final EntityManager entityManager;

    public CategoryImageDBServiceImpl(CategoryImageDBRepository categoryImageDBRepository, EntityManager entityManager) {
        this.categoryImageDBRepository = categoryImageDBRepository;
        this.entityManager = entityManager;
    }

    public Optional<CategoryImageDB> findById(Long id) {
        return categoryImageDBRepository.findById(id);
    }

    @Override
    public Optional<CategoryImageDB> findImageIdById(Category id) {
        return categoryImageDBRepository.findImageIdById(id);
    }

    @Override
    public CategoryImageDB store(MultipartFile multipartFile) {
        try {
            String imageName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Date now = new Date();
            long longTimeNow = now.getTime();
            String imageNameFull = longTimeNow + "-" + imageName;
            CategoryImageDB categoryImageDB = new CategoryImageDB();
            categoryImageDB.setName(imageNameFull);
            categoryImageDB.setType(multipartFile.getContentType());
            categoryImageDB.setImageDB(ImageDBUtility.compressImageDB(multipartFile.getBytes()));
            System.out.println("categoryImageDB " + categoryImageDB);
            return categoryImageDBRepository.save(categoryImageDB);
        } catch (Exception e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public void update(Long id, MultipartFile multipartFile) {
        findById(id).map(categoryImageDB -> {
            String imageName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Date now = new Date();
            long longTimeNow = now.getTime();
            String imageNameFull = longTimeNow + "-" + imageName;
            categoryImageDB.setName(imageNameFull);
            categoryImageDB.setType(multipartFile.getContentType());
            try {
                categoryImageDB.setImageDB(ImageDBUtility.compressImageDB(multipartFile.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(categoryImageDBRepository, categoryImageDB);
            return categoryImageDBRepository.save(categoryImageDB);
        }).orElseThrow(() -> new ResourceNotFoundException("Category Image", ID, id));
    }

    public CategoryImageDB getImageDB(String name) {
        return categoryImageDBRepository.findCategoryImageDBByName(name);
    }

    public Stream<CategoryImageDB> getAllImageDB() {
        return categoryImageDBRepository.findAll().stream();
    }

    public Iterable<CategoryImageDB> findAll(boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedCategoryImageDBFilter");
        filter.setParameter("isDeleted", isDeleted);
        Iterable<CategoryImageDB> categoryImageDBList =  categoryImageDBRepository.findAll();
        session.disableFilter("deletedCategoryImageDBFilter");
        return categoryImageDBList;
    }
}
