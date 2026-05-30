package com.shopsphere.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.cart.dto.AddToCartRequest;
import com.shopsphere.cart.dto.CartResponse;
import com.shopsphere.cart.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addtoCart(
			@Valid @RequestBody AddToCartRequest request){
		
		return ResponseEntity.ok(
				cartService.addToCart(request));
	}
	
	@GetMapping("/get")
	public ResponseEntity<CartResponse> getCart(){
		
		return ResponseEntity.ok(
				cartService.getCart());
	}
	
	@DeleteMapping("/remove/{productId}")
	public ResponseEntity<String> removeItem(@PathVariable Long productId){
		
		return ResponseEntity.ok(cartService.removeItem(productId));
	}
	
	@DeleteMapping("/clear")
	public ResponseEntity<String> clearCart() {

	    return ResponseEntity.ok(
	            cartService.clearCart());
	}
}
