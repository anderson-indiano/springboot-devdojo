package com.devdojo.springboot.error;

public class CustomerErrorType {
	
	private String errorMessage;

	public CustomerErrorType(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
