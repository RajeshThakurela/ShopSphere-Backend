package com.shopsphere.auth.dto;

import com.shopsphere.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

	@NotBlank(message="Full name is required")
	private String fullName;
	@Email(message="Invalid email format")
	@NotBlank(message="Email is required")
	
	private String email;
	@Size(min=8,message="Password must be at least 8 characters")
	private String password;
	@NotBlank(message="phone no is required")
	private String phone;
	@NotNull(message="Role is required")
	private Role role;
	
}
