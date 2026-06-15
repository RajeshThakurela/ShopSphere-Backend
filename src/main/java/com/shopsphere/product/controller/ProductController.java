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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name="bearerAuth")
@Tag(name = "Product APIs", description = "Product management")
@RestController
@RequestMapping("/api/seller/products")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
//	create product
	@Operation(summary = "Create a new product")
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "201",
	        description = "Product created successfully"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Invalid input data"
	    ),
	    @ApiResponse(
	        responseCode = "401",
	        description = "Unauthorized"
	    ),
	    @ApiResponse(
	        responseCode = "403",
	        description = "Access denied"
	    ),
	    @ApiResponse(
	        responseCode = "404",
	        description = "Category not found"
	    )
	})
	@PostMapping("/save")
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
		
		return new ResponseEntity<>(
				productService.createProduct(request),HttpStatus.CREATED);
	}
	
//	Delete Product
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Product updated successfully"),
	    @ApiResponse(responseCode = "400", description = "Validation failed"),
	    @ApiResponse(responseCode = "403", description = "Seller access required"),
	    @ApiResponse(responseCode = "404", description = "Product not found")
	})
	@PutMapping("/update/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
			@Valid @RequestBody ProductRequest request){
		
		return ResponseEntity.ok(productService.updateProduct(id, request));
	}
	
//	Update Product
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
	    @ApiResponse(responseCode = "403", description = "Seller access required"),
	    @ApiResponse(responseCode = "404", description = "Product not found")
	})
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		
		return ResponseEntity.ok(productService.deleteProduct(id));
	}

}
