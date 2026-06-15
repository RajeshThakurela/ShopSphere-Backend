package com.shopsphere.category.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.category.dto.CategoryRequest;
import com.shopsphere.category.dto.CategoryResponse;
import com.shopsphere.category.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name="bearerAuth")
@Tag(name = "Category APIs", description = "Category management")
@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	
	@Operation(summary = "Create a new Category")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Category created successfully"),
	    @ApiResponse(responseCode = "409", description = "Category already exists")
	})
	@PostMapping("/save")
	public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request){
		
		return new ResponseEntity<>(
				categoryService.createCategory(request),
				HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update Category by id")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Category updated successfully"),
	    @ApiResponse(responseCode = "404", description = "Category not found")
	})
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,@RequestBody CategoryRequest request){
		
		return ResponseEntity.ok(
				categoryService.updateCategory(id, request));
	}
	
	@Operation(summary = "Delete Category by id")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
	    @ApiResponse(responseCode = "404", description = "Category not found"),
	    @ApiResponse(responseCode = "409", description = "Category contains products")
	})
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id){
		
		return ResponseEntity.ok(categoryService.deleteCategory(id));
	}
}
