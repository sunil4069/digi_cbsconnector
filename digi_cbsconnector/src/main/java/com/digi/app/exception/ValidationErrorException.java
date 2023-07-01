package com.digi.app.exception;

import org.springframework.validation.BindingResult;

public class ValidationErrorException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BindingResult bindingResult;
	public ValidationErrorException(BindingResult bindingResult) {
		this.bindingResult=bindingResult;
	}

}
