package com.shopsphere.cart.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartResponse {

	private Long cartId;
	
	private Long userId;
	
	private List<CartItemResponse> items;
	
	private BigDecimal totalAmount;
}
