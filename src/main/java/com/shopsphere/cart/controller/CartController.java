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

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SecurityRequirement(name="bearerAuth")
@Tag(name = "Cart APIs", description = "Shopping cart operations")
@RestController
@RequestMapping("/api/customer/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;
	
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Product added to cart"),
	    @ApiResponse(responseCode = "400", description = "Insufficient stock"),
	    @ApiResponse(responseCode = "404", description = "Product not found")
	})
	@PostMapping("/add")
	public ResponseEntity<String> addtoCart(
			@Valid @RequestBody AddToCartRequest request){
		
		return ResponseEntity.ok(
				cartService.addToCart(request));
	}
	
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Cart fetched successfully"),
	    @ApiResponse(responseCode = "404", description = "Cart not found")
	})
	@GetMapping("/get")
	public ResponseEntity<CartResponse> getCart(){
		
		return ResponseEntity.ok(
				cartService.getCart());
	}
	
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Item removed successfully"),
	    @ApiResponse(responseCode = "404", description = "Product not found in cart")
	})
	@DeleteMapping("/remove/{productId}")
	public ResponseEntity<String> removeItem(@PathVariable Long productId){
		
		return ResponseEntity.ok(cartService.removeItem(productId));
	}
	
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Cart cleared successfully"),
	    @ApiResponse(responseCode = "404", description = "Cart not found")
	})
	@DeleteMapping("/clear")
	public ResponseEntity<String> clearCart() {

	    return ResponseEntity.ok(
	            cartService.clearCart());
	}
}
