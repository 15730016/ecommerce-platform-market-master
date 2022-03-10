package com.sintkit.ecommerceservice.service.image;

import com.sintkit.ecommerceservice.exception.ResourceNotFoundException;
import com.sintkit.ecommerceservice.exception.StorageException;
import com.sintkit.ecommerceservice.model.Category;
import com.sintkit.ecommerceservice.model.CategoryImageDB;
import com.sintkit.ecommerceservice.repository.CategoryImageDBRepository;
import com.sintkit.ecommerceservice.util.ImageDBUtility;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.sintkit.ecommerceservice.config.AppConstants.ID;

@Service
public class CategoryImageDBServiceImpl implements CategoryImageDBService {

    private final CategoryImageDBRepository categoryImageDBRepository;

    public CategoryImageDBServiceImpl(CategoryImageDBRepository categoryImageDBRepository) {
        this.categoryImageDBRepository = categoryImageDBRepository;
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
}
