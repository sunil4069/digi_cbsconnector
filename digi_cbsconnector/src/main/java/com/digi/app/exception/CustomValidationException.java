package com.digi.app.exception;

import java.util.List;

public class CustomValidationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public List<String> errorMessages;

	public CustomValidationException(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	
	

}
