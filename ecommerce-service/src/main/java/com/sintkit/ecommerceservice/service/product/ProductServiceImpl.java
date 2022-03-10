package com.sintkit.ecommerceservice.service.product;

import com.sintkit.ecommerceservice.dto.ProductRequest;
import com.sintkit.ecommerceservice.exception.ResourceNotFoundException;
import com.sintkit.ecommerceservice.model.Product;
import com.sintkit.ecommerceservice.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sintkit.ecommerceservice.config.AppConstants.ID;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product save(ProductRequest productRequest) {
		Product product = new Product();
		BeanUtils.copyProperties(productRequest, product);
		return productRepository.save(product);
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public void update(Long id, ProductRequest productRequest) {
		findById(id).map(product -> {
			BeanUtils.copyProperties(productRequest, product);
			return productRepository.save(product);
		}).orElseThrow(() -> new ResourceNotFoundException("Product", ID, id));
	}
}
