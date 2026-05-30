package com.shopsphere.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopsphere.entity.Cart;
import com.shopsphere.entity.User;

public interface CartRepository extends JpaRepository<Cart,Long>{

	Optional<Cart> findByUser(User user);
}
