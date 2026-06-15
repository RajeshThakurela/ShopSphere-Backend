package com.shopsphere.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.category.dto.CategoryResponse;
import com.shopsphere.category.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Public Category APIs",
	    description = "Public Category browsing APIs"
	)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryPublicController {

	private final CategoryService categoryService;
	
	@GetMapping("/get")
	public ResponseEntity<List<CategoryResponse>> getAllCategories(){
		
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id){
		
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}
}
