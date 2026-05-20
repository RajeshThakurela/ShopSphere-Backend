package com.shopsphere.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopsphere.entity.Category;
import com.shopsphere.entity.Product;



public interface ProductRepository extends JpaRepository<Product,Long>{
	
	public List<Product> findByCategory(Category category);
	
	public List<Product> findByNameContainingIgnoreCase(String keyword);

}

