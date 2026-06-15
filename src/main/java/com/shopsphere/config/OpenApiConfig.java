package com.shopsphere.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
		info=@Info(
				title="ShopSphere API",
				version="1.0",
				description="E-Commerce Backend API",
				contact = @Contact(
				    name = "Rajesh Kumar",
					email = "rajeshkumarup2001@gmail.com",
					url = "https://github.com/RajeshThakurela"
				)
		)
)

@SecurityScheme(
		name="BearerAuth",
		type=SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat="JWT")
public class OpenApiConfig {

}
