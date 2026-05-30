package com.shopsphere.cart.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {

	private Long productId;
	
	private String productName;
	
	private BigDecimal price;
	
	private Integer quantity;
	
	private BigDecimal subTotal;
}
