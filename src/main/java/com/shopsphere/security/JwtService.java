package com.shopsphere.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
//	convert string secret key to key object
	private SecretKey getSignKey() {
		byte[] keyBytes = secret.getBytes();
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
//	generate token
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getSignKey())
				.compact();
	}
	
//	extract username from token
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}
	
//	validate token
	public boolean isTokenValid(String token,UserDetails userDetails) {
		String username=extractUsername(token);
		
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
//	check expire
	private boolean isTokenExpired(String token) {
		return extractAllClaims(token)
				.getExpiration()
				.before(new Date());
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
