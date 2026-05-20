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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	
	@PostMapping("/save")
	public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request){
		
		return new ResponseEntity<>(
				categoryService.createCategory(request),
				HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,@RequestBody CategoryRequest request){
		
		return ResponseEntity.ok(
				categoryService.updateCategory(id, request));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id){
		
		return ResponseEntity.ok(categoryService.deleteCategory(id));
	}
}
