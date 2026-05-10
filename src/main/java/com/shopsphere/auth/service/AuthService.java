package com.shopsphere.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopsphere.auth.dto.AuthResponse;
import com.shopsphere.auth.dto.LoginRequest;
import com.shopsphere.auth.dto.RegisterRequest;
import com.shopsphere.entity.User;
import com.shopsphere.security.JwtService;
import com.shopsphere.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthResponse register(RegisterRequest request) {
		User user = User.builder()
				.fullName(request.getFullName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.phone(request.getPhone())
				.role(request.getRole())
				.enabled(true)
				.build();
		
		userRepository.save(user);
		
		String token=jwtService.generateToken(
				new org.springframework.security.core.userdetails.User(
						user.getEmail(),
						user.getPassword(),
						java.util.Collections.emptyList()));
		
		return new AuthResponse(token);
	}
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()));
		
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		
		 String token =
	                jwtService.generateToken(
	                        new org.springframework.security.core.userdetails.User(
	                                user.getEmail(),
	                                user.getPassword(),
	                                java.util.Collections.emptyList()
	                        )
	                );
		 
		 return new AuthResponse(token);
				
	}
	
	
}
