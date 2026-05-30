package com.shopsphere.cart.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shopsphere.cart.dto.AddToCartRequest;
import com.shopsphere.cart.dto.CartItemResponse;
import com.shopsphere.cart.dto.CartResponse;
import com.shopsphere.cart.repository.CartItemRepository;
import com.shopsphere.cart.repository.CartRepository;
import com.shopsphere.entity.Cart;
import com.shopsphere.entity.CartItem;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.User;
import com.shopsphere.exception.BadRequestException;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.product.repository.ProductRepository;
import com.shopsphere.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	
	private User getCurrentUser() {
		String email = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
		
		return userRepository.findByEmail(email)
				.orElseThrow(()->
						new ResourceNotFoundException("user not found"));
	}
	
	public String addToCart(AddToCartRequest request) {
		User user = getCurrentUser();
		
		Product product = productRepository
				.findById(request.getProductId())
				.orElseThrow(()->
						new ResourceNotFoundException("product not found"));
		
	
		Cart cart = cartRepository
				.findByUser(user)
				.orElse(null);
		
		if(cart==null) {
			cart = Cart.builder()
					.user(user)
					.build();
			
			cart = cartRepository.save(cart);
		}
		
		CartItem cartItem = cartItemRepository
				.findByCartAndProduct(cart, product)
				.orElse(null);
		
		if(cartItem!=null) {
			
			int newQuantity = cartItem.getQuantity() +
							request.getQuantity();
					
			if(newQuantity > product.getStock()) {
		        throw new BadRequestException(
		                "Insufficient stock available");
		    }

		    cartItem.setQuantity(newQuantity);
		}else {
			cartItem = CartItem.builder()
					.cart(cart)
					.product(product)
					.quantity(request.getQuantity())
					.build();
		}
		
		cartItemRepository.save(cartItem);
		
		return "product added to cart successfull.";
	}
	
	public CartResponse getCart() {
		User user = getCurrentUser();
		
		Cart cart = cartRepository.findByUser(user)
				.orElseThrow(()->
				new ResourceNotFoundException("Cart not found"));
		
		List<CartItemResponse> items = cart.getCartItems()
				.stream()
				.map(item ->{
					BigDecimal subTotal = item.getProduct().getPrice()
							.multiply(BigDecimal.valueOf(item.getQuantity()));
					
					return CartItemResponse.builder()
							.productId(item.getProduct().getId())
							.productName(item.getProduct().getName())
							.price(item.getProduct().getPrice())
							.quantity(item.getQuantity())
							.subTotal(subTotal)
							.build();
							
				})
				.toList();
		
		BigDecimal totalAmount = items.stream()
				.map(CartItemResponse::getSubTotal)
				.reduce(
						BigDecimal.ZERO,
						BigDecimal::add);
		
		return CartResponse.builder()
				.cartId(cart.getId())
	            .userId(user.getId())
	            .items(items)
	            .totalAmount(totalAmount)
	            .build();
				
	}
	
	public String removeItem(Long productId) {
		
		User user = getCurrentUser();
		
		Cart cart = cartRepository.findByUser(user)
				.orElseThrow(()->
						new ResourceNotFoundException("Cart not found."));
		
		Product product = productRepository.findById(productId)
				.orElseThrow(()->
						new ResourceNotFoundException("Product not found."));
		
		CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
					.orElseThrow(()->
							new ResourceNotFoundException("Product not found in cart"));
		
		cartItemRepository.delete(cartItem);
		
		return "Item removed successfully";
	}
	
	public String clearCart() {
		User user = getCurrentUser();
		
		Cart cart = cartRepository.findByUser(user)
				.orElseThrow(()->
						new ResourceNotFoundException("Cart not found."));
		
		cart.getCartItems().clear();
		
		cartRepository.save(cart);
		
		return "cart cleared successfully.";
	}
}
