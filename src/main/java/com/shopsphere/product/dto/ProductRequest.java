package com.shopsphere.product.dto;


import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductRequest {

	@NotBlank
	private String name;
	
	private String description;
	
	@NotNull
	@Positive
	private BigDecimal price;
	
	@NotNull
	@PositiveOrZero
	private Integer stock;
	
	@NotBlank
	private String brand;
	
	private Long category_id;
	
	private List<String > imageUrls;
}
