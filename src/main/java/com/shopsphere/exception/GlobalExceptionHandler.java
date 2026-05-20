package com.shopsphere.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shopsphere.exception.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(
			ResourceNotFoundException ex){
		
		ErrorResponse error =new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				"NOT_FOUND",
				ex.getMessage());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex){
		
		ErrorResponse error= new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.CONFLICT.value(),
				"CONFLICT",
				ex.getMessage());
		
		return new ResponseEntity<>(
				error,
				HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex){
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"BAD_REQUEST",
				ex.getMessage());
		
		return new ResponseEntity<>(
				error,
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>>
	handleValidationException(
	        MethodArgumentNotValidException ex){

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult()
	            .getFieldErrors()
	            .forEach(error ->
	                    errors.put(
	                            error.getField(),
	                            error.getDefaultMessage()
	                    ));

	    return new ResponseEntity<>(
	            errors,
	            HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex){
		
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.FORBIDDEN.value(),
				"FORBIDDEN",
				"You are not authorized to access this resource");
		
		return new ResponseEntity<>(
				error,
				HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
		
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.UNAUTHORIZED.value(),
				"UNAUTHORIZED",
				"Invalid Email or Password");
		
		return new ResponseEntity<>(
				error,
				HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse>
	handleDatabaseException(
	        DataIntegrityViolationException ex){

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.CONFLICT.value(),
	            "DATABASE_ERROR",
	            "Database constraint violation");

	    return new ResponseEntity<>(
	            error,
	            HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse>
	handleGlobalException(Exception ex){

	    ErrorResponse error = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.INTERNAL_SERVER_ERROR.value(),
	            "INTERNAL_SERVER_ERROR",
	            ex.getMessage());

	    return new ResponseEntity<>(
	            error,
	            HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
