package com.shopsphere.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopsphere.entity.ProductImage;

public interface ProductImageRepository
        extends JpaRepository<ProductImage, Long> {

}