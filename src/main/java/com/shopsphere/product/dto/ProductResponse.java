package com.shopsphere.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

	private Long id;
	
	private String name;
	
	private String description;
	
	private BigDecimal price;
	
	private Integer  stock;
	
	private String brand;
	
	private String categoryName;
	
	private List<String> imageUrls;
	
	private LocalDateTime createdAt;
}
