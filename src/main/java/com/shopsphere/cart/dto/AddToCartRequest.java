package com.shopsphere.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequest {

	@NotNull(message="product id is required")
	private Long productId;
	
	@NotNull(message="quantity is required")
	@Min(value=1,message="quantity must be greater than 0")
	private Integer quantity;
}
