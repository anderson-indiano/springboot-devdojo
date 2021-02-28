package com.devdojo.springboot.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.devdojo.springboot.error.ErrorArgumentResolvers;
import com.devdojo.springboot.error.ErrorDetails;
import com.devdojo.springboot.error.ResourceNotFoundDetails;
import com.devdojo.springboot.error.ResourceNotFoundException;
import com.devdojo.springboot.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResponseNotFoundException(ResourceNotFoundException resourceNotFoundException) {
		    ResourceNotFoundDetails resourceNotFoundDetails = ResourceNotFoundDetails.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.NOT_FOUND.value())
			.title("Resource not found")
			.detail(resourceNotFoundException.getMessage())
			.developerMessage(resourceNotFoundException.getClass().getName())
			.build();
		return new ResponseEntity<>(resourceNotFoundDetails, HttpStatus.NOT_FOUND);
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException, 
																HttpHeaders headers, 
																HttpStatus status, 
																WebRequest request) {
			
				
		List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
			String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
			String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
			ValidationErrorDetails validationErrorDetails = ValidationErrorDetails.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.BAD_REQUEST.value())
			.title("Field Validation Error")
			.detail("Field Validation Error")
			.developerMessage(methodArgumentNotValidException.getClass().getName())
			.field(fields)
			.fieldMessage(fieldMessages)
			.build();
		return new ResponseEntity<>(validationErrorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<?> handleResponseNotFoundException(PropertyReferenceException propertyReferenceException) {
		ErrorArgumentResolvers errorArgumentResolvers = ErrorArgumentResolvers.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.NOT_FOUND.value())
			.title("Resource not found")
			.detail("Arguments incorret")
			.developerMessage(propertyReferenceException.getClass().getName())
			.build();
		return new ResponseEntity<>(errorArgumentResolvers, HttpStatus.NOT_FOUND);
	}
	
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException httpMessageNotReadableException, 
																HttpHeaders headers, 
																HttpStatus status, 
																WebRequest request) {
		
		ErrorDetails errorDetails = ErrorDetails.Builder
				.newBuilder()
				.timestamp(new Date().getTime())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("Resource not found")
				.detail(httpMessageNotReadableException.getMessage())
				.developerMessage(httpMessageNotReadableException.getClass().getName())
				.build();
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex,Object body, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

		ErrorDetails errorDetails = ErrorDetails.Builder
				.newBuilder()
				.timestamp(new Date().getTime())
				.status(status.value())
				.title("Internal Exception")
				.detail(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.build();
		return new ResponseEntity<>(errorDetails, headers, status);
	}
	

}
