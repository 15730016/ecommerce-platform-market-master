package com.sintkit.ecommerceservice.service.product;

import com.sintkit.ecommerceservice.dto.ColorRequest;
import com.sintkit.ecommerceservice.exception.ResourceNotFoundException;
import com.sintkit.ecommerceservice.model.Color;
import com.sintkit.ecommerceservice.repository.ColorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sintkit.ecommerceservice.config.AppConstants.ID;

@Service
public class ColorServiceImpl implements ColorService{

    private final ColorRepository colorRepository;

    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public Color save(ColorRequest colorRequest) {
        Color color = new Color();
        BeanUtils.copyProperties(colorRequest, color);
        return colorRepository.save(color);
    }

    @Override
    public void deleteById(Long id) {
        colorRepository.deleteById(id);
    }

    @Override
    public Optional<Color> findById(Long id) {
        return colorRepository.findById(id);
    }

    @Override
    public Page<Color> findAll(Pageable pageable) {
        return colorRepository.findAll(pageable);
    }

    @Override
    public void update(Long id, ColorRequest colorRequest) {
        findById(id).map(color -> {
            BeanUtils.copyProperties(colorRequest, color);
            return colorRepository.save(color);
        }).orElseThrow(() -> new ResourceNotFoundException("Color", ID, id));
    }

}
