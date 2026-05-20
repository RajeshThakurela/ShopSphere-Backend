package com.shopsphere.category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shopsphere.category.dto.CategoryRequest;
import com.shopsphere.category.dto.CategoryResponse;
import com.shopsphere.category.repository.CategoryRepository;
import com.shopsphere.entity.Category;
import com.shopsphere.exception.BadRequestException;
import com.shopsphere.exception.DuplicateResourceException;
import com.shopsphere.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;
	
//	create category
	public CategoryResponse createCategory(CategoryRequest request) {
		
//		check duplicate category
		categoryRepository.findByName(request.getName())
			.ifPresent(category->{
					throw new DuplicateResourceException("Category already exists!");
					});
		

		Category category=Category.builder()
				.name(request.getName())
				.description(request.getDescription())
				.build();
		
		Category savedCategory=categoryRepository.save(category);
		
		return CategoryResponse.builder()
				.id(savedCategory.getId())
				.name(savedCategory.getName())
				.description(savedCategory.getDescription())
				.build();
	}
	
//	Get all Categories
	public List<CategoryResponse> getAllCategories(){
		
		return categoryRepository.findAll()
				.stream()
				.map(category->
					CategoryResponse.builder()
					.id(category.getId())
					.name(category.getName())
					.description(category.getDescription())
					.build()
				)
				.collect(Collectors.toList());
		
	}
	
//	get category by id
	public CategoryResponse getCategoryById(Long id){

	    Category category = categoryRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Category not found!"));

	    return mapToResponse(category);
	}
	
//	delete category by id
	public String deleteCategory(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
		
//		check product exist or not
		if(!category.getProduct().isEmpty()) {
			throw new BadRequestException("Cannot delete category with products.");
		}
		
		categoryRepository.delete(category);
		
		return "category delete successfully.";
		
	}
	
//	update category
	public CategoryResponse updateCategory(Long id,CategoryRequest request) {
		
		Category category = categoryRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
		
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		
		Category updatedCategory = categoryRepository.save(category);
		
		return mapToResponse(updatedCategory);
	}
	
//	Entity to DTO
	public CategoryResponse mapToResponse(Category category) {
		
		return CategoryResponse.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.build();
	}
}
