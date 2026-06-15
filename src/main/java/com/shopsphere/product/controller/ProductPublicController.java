package com.shopsphere.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.product.dto.ProductResponse;
import com.shopsphere.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Public Product APIs",
	    description = "Public product browsing APIs"
	)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductPublicController {

	private final ProductService productService;
	
	@Operation(summary = "Get all products")
	@GetMapping("/get")
	public ResponseEntity<List<ProductResponse>> getAllProducts(){
		
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@Operation(summary = "Get product by id")
	@GetMapping("/get/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id ){
		
		return ResponseEntity.ok(productService.getProductById(id));
	}
}
