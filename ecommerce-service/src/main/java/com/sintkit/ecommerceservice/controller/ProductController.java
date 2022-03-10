package com.sintkit.ecommerceservice.controller;

import com.sintkit.ecommerceservice.config.AppConstants;
import com.sintkit.ecommerceservice.dto.*;
import com.sintkit.ecommerceservice.exception.ResourceNotFoundException;
import com.sintkit.ecommerceservice.model.*;
import com.sintkit.ecommerceservice.repository.CategoryImageDBRepository;
import com.sintkit.ecommerceservice.service.image.CategoryImageDBService;
import com.sintkit.ecommerceservice.service.image.ImageDBStorageService;
import com.sintkit.ecommerceservice.service.product.CategoryService;
import com.sintkit.ecommerceservice.service.product.ColorService;
import com.sintkit.ecommerceservice.service.product.ProductService;
import com.sintkit.ecommerceservice.service.product.SizeService;
import com.sintkit.ecommerceservice.util.ImageDBUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static com.sintkit.ecommerceservice.config.AppConstants.ID;

@RestController
@RequestMapping(path = "/api/management")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;
	private final ColorService colorService;
	private final SizeService sizeService;
	private final ImageDBStorageService imageStorageService;
	private final CategoryImageDBRepository categoryImageDBRepository;
	private final CategoryImageDBService categoryImageDBService;

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(100000);
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		return commonsMultipartResolver;
	}


	// ---- Product ---- //
	@GetMapping("/product")
	public Page<Product> getProductList(@RequestParam(defaultValue = "0") int page,
										@RequestParam(defaultValue = "3") int size) {
		Pageable paging = PageRequest.of(page, size);
		return productService.findAll(paging);
	}

	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable Long id) {
		return productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", ID, id));
	}

	@PostMapping("/product")
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) throws IOException {
		Date now = new Date();
		long longTimeNow = now.getTime();
		productRequest.setProductCreatedDate(longTimeNow);
		productRequest.setProductUpdateDate(longTimeNow);
		productService.save(productRequest);
		return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id,
										   @Valid @RequestBody ProductRequest productRequest) {
		Date now = new Date();
		long longTimeUpdate = now.getTime();
		productRequest.setProductCreatedDate(productRequest.getProductCreatedDate());
		productRequest.setProductUpdateDate(longTimeUpdate);
		productService.update(id, productRequest);
		return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		return productService.findById(id).map(product -> {
			productService.deleteById(id);
			return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
		}).orElseThrow(() -> new ResourceNotFoundException("Product", ID, id));
	}

	// ---- Product Category---- //

	@GetMapping("/category")
	public Page<Category> getCategoryList(@RequestParam(defaultValue = "0") int page,
										  @RequestParam(defaultValue = "3") int size) {
		Pageable paging = PageRequest.of(page, size);
		return categoryService.findAll(paging);
	}

	@GetMapping("/category/{id}")
	public Category getCategory(@PathVariable Long id) {
		return categoryService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Category", ID, id));
	}

	@PostMapping(value = "/category", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> createCategory(@ModelAttribute CategoryRequest categoryRequest,
											@RequestParam("image") MultipartFile multipartFile) throws IOException {
		CategoryImageDB categoryImageDB = categoryImageDBService.store(multipartFile);
		categoryRequest.setCategoryImageDB(categoryImageDB);
		categoryService.save(categoryRequest);

		return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
	}

	@GetMapping("/images/{name}")
	public ResponseEntity<byte[]> getFile(@PathVariable String name) {
		CategoryImageDB categoryImageDB = categoryImageDBService.getImageDB(name);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + categoryImageDB.getName() + "\"")
				.body(ImageDBUtility.decompressImageDB(categoryImageDB.getImageDB()));
	}

	@PutMapping("/category/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable Long id,
											@ModelAttribute CategoryRequest categoryRequest,
											@RequestParam("image") MultipartFile multipartFile) {
		System.out.println("id = " + id);
		System.out.println("categoryRequest = " + categoryRequest);
		System.out.println("multipartFile = " + multipartFile);
		CategoryImageDB categoryImageDB = categoryRequest.getCategoryImageDB();
		System.out.println("categoryImageDB = " + categoryImageDB);
		Long imageId = categoryImageDB.getId();
		System.out.println("imageId = " + imageId);
		categoryImageDBService.update(imageId, multipartFile);
		categoryRequest.setCategoryImageDB(categoryImageDB);
		categoryService.update(id, categoryRequest);


		return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
	}

	@DeleteMapping("/category/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		return categoryService.findById(id).map(category -> {
			categoryService.deleteById(id);
			return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
		}).orElseThrow(() -> new ResourceNotFoundException("Category ", ID, id));
	}

	// ---- Product Color---- //

	@GetMapping("/color")
	public Page<Color> getColorList(@RequestParam(defaultValue = "0") int page,
									@RequestParam(defaultValue = "3") int size) {
		Pageable paging = PageRequest.of(page, size);
		return colorService.findAll(paging);
	}

	@GetMapping("/color/{id}")
	public Color getColor(@PathVariable Long id) {
		return colorService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Color ", ID, id));
	}

	@PostMapping("/color")
	public ResponseEntity<?> createColor(@Valid @RequestBody ColorRequest colorRequest) throws IOException {
		colorService.save(colorRequest);
		return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
	}

	@PutMapping("/color/{id}")
	public ResponseEntity<?> updateColor(@PathVariable Long id,
												   @Valid @RequestBody ColorRequest colorRequest) {
		colorService.update(id, colorRequest);
		return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
	}

	@DeleteMapping("/color/{id}")
	public ResponseEntity<?> deleteColor(@PathVariable Long id) {
		return colorService.findById(id).map(color -> {
			colorService.deleteById(id);
			return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
		}).orElseThrow(() -> new ResourceNotFoundException("Color ", ID, id));
	}

	// ---- Product Size---- //

	@GetMapping("/size")
	public Page<Size> getSizeList(@RequestParam(defaultValue = "0") int page,
								  @RequestParam(defaultValue = "3") int size) {
		Pageable paging = PageRequest.of(page, size);
		return sizeService.findAll(paging);
	}

	@GetMapping("/size/{id}")
	public Size getSize(@PathVariable Long id) {
		return sizeService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Size ", ID, id));
	}

	@PostMapping("/size")
	public ResponseEntity<?> createSize(@Valid @RequestBody SizeRequest sizeRequest) throws IOException {
		sizeService.save(sizeRequest);
		return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
	}

	@PutMapping("/size/{id}")
	public ResponseEntity<?> updateSize(@PathVariable Long id,
												@Valid @RequestBody SizeRequest sizeRequest) {
		sizeService.update(id, sizeRequest);
		return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
	}

	@DeleteMapping("/size/{id}")
	public ResponseEntity<?> deleteSize(@PathVariable Long id) {
		return sizeService.findById(id).map(size -> {
			sizeService.deleteById(id);
			return ResponseEntity.ok().body(new ApiResponse(true, AppConstants.SUCCESS));
		}).orElseThrow(() -> new ResourceNotFoundException("Size ", ID, id));
	}
}
