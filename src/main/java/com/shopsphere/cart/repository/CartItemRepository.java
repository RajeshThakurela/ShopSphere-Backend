package com.shopsphere.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopsphere.entity.Cart;
import com.shopsphere.entity.CartItem;
import com.shopsphere.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

	Optional<CartItem> findByCartAndProduct(Cart cart,Product product);
}
