package com.waracle.cakemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CakeNotFoundException extends Exception {
	
	private static final long serialVersionUID = -5401308558866080725L;

	public CakeNotFoundException(String message) {
		super();
	}
	
}
