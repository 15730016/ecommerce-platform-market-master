package com.sintkit.ecommerceservice.service.image;

import com.sintkit.ecommerceservice.model.ImageDB;
import com.sintkit.ecommerceservice.repository.ImageDBRepository;
import com.sintkit.ecommerceservice.util.ImageDBUtility;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ImageDBStorageService {

    private ImageDBRepository imageRepository;

    public ImageDBStorageService(ImageDBRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageDB save(MultipartFile multipartFile) throws IOException {
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        ImageDB imageDB = new ImageDB();
        imageDB.setName(imageName);
        imageDB.setType(multipartFile.getContentType());
        imageDB.setImageDB(ImageDBUtility.compressImageDB(multipartFile.getBytes()));
        return imageRepository.save(imageDB);
    }

    public ImageDB findImageDBById(Long id) {
        return imageRepository.findImageDBById(id);
    }

    public ImageDB getImageDB(Long id, String name) {
        imageRepository.existsById(id);
        return imageRepository.findImageDBByName(name);
    }

    public Stream<ImageDB> getAllImageDB() {
        return imageRepository.findAll().stream();
    }
}
