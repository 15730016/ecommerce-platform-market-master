package com.sintkit.ecommerceservice.service.product;

import com.sintkit.ecommerceservice.dto.SizeRequest;
import com.sintkit.ecommerceservice.exception.ResourceNotFoundException;
import com.sintkit.ecommerceservice.model.Size;
import com.sintkit.ecommerceservice.model.Size;
import com.sintkit.ecommerceservice.repository.SizeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sintkit.ecommerceservice.config.AppConstants.ID;

@Service
public class SizeServiceImpl implements SizeService{

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public Size save(SizeRequest sizeRequest) {
        Size size = new Size();
        BeanUtils.copyProperties(sizeRequest, size);
        return sizeRepository.save(size);
    }

    @Override
    public void deleteById(Long id) {
        sizeRepository.deleteById(id);
    }

    @Override
    public Optional<Size> findById(Long id) {
        return sizeRepository.findById(id);
    }

    @Override
    public Page<Size> findAll(Pageable pageable) {
        return sizeRepository.findAll(pageable);
    }

    @Override
    public void update(Long id, SizeRequest sizeRequest) {
        findById(id).map(size -> {
            BeanUtils.copyProperties(sizeRequest, size);
            return sizeRepository.save(size);
        }).orElseThrow(() -> new ResourceNotFoundException("Size", ID, id));
    }

}
