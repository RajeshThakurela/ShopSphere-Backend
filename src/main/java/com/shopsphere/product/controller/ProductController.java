package com.shopsphere.product.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.product.dto.ProductRequest;
import com.shopsphere.product.dto.ProductResponse;
import com.shopsphere.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seller/products")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
//	create product
	@PostMapping("/save")
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
		
		return new ResponseEntity<>(
				productService.createProduct(request),HttpStatus.CREATED);
	}
	
//	Delete Product
	@PutMapping("/update/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
			@Valid @RequestBody ProductRequest request){
		
		return ResponseEntity.ok(productService.updateProduct(id, request));
	}
	
//	Update Product
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		
		return ResponseEntity.ok(productService.deleteProduct(id));
	}

}
