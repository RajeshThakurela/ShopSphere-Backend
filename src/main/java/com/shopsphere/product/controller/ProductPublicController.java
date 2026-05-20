package com.shopsphere.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.product.dto.ProductResponse;
import com.shopsphere.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductPublicController {

	private final ProductService productService;
	
	@GetMapping("/get")
	public ResponseEntity<List<ProductResponse>> getAllProducts(){
		
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id ){
		
		return ResponseEntity.ok(productService.getProductById(id));
	}
}
