package com.shopsphere.product.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shopsphere.category.repository.CategoryRepository;
import com.shopsphere.entity.Category;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.ProductImage;
import com.shopsphere.product.dto.ProductRequest;
import com.shopsphere.product.dto.ProductResponse;
import com.shopsphere.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	public ProductResponse createProduct(ProductRequest request) {
		Category category = categoryRepository.findById(request.getCategory_id())
				.orElseThrow(()->new RuntimeException("category not found."));
		
		Product product = Product.builder()
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.stock(request.getStock())
				.brand(request.getBrand())
				.category(category)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
		
//		CREATE IMAGES
		List<ProductImage> images=
				request.getImageUrls()
					.stream()
					.map(url->
							ProductImage.builder()
							.imageUrl(url)
							.product(product)
							.build()
					)
					.collect(Collectors.toList());
		
		product.setImages(images);
		
		Product savedProduct = productRepository.save(product);
		
		return mapToResponse(savedProduct);
				
	}
	
//	Get All Products
	public List<ProductResponse> getAllProducts(){
		return productRepository.findAll()
					.stream()
					.map(this::mapToResponse)
					.collect(Collectors.toList());
	}
	
//	Get Product By id
	public ProductResponse getProductById(Long id) {
		
		Product product = productRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Product not found!"));
		
		return mapToResponse(product);
	}
	
//	Update Product
	public ProductResponse updateProduct(Long id,ProductRequest request) {
		Product product = productRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Product not found!"));
		
		Category category = categoryRepository.findById(
				request.getCategory_id())
				.orElseThrow(()->new RuntimeException("No category found!"));
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		product.setBrand(request.getBrand());
		product.setCategory(category);
		product.setUpdatedAt(LocalDateTime.now());
		
//		Clear old images
		product.getImages().clear();
		
//		add new images
		
		List<ProductImage> images = request.getImageUrls()
				.stream()
				.map(url->ProductImage.builder()
						.imageUrl(url)
						.product(product)
						.build())
				.collect(Collectors.toList());
		
		product.getImages().addAll(images);
		
		Product updateProduct = productRepository.save(product);
		
		return mapToResponse(updateProduct);
	}
	
//	Delete Product
	public String deleteProduct(Long id){
		
		Product product = productRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Product not found!"));
		
		productRepository.delete(product);
		
		return "Product deleted successfully.";
	}
	
//	Entity to DTO
	private ProductResponse mapToResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.stock(product.getStock())
				.brand(product.getBrand())
				.categoryName(
						product.getCategory().getName())
				.imageUrls(
						product.getImages()
							.stream()
							.map(ProductImage::getImageUrl)
							.collect(Collectors.toList())
				)
				.createdAt(product.getCreatedAt())
				.build();
				
	}

}
