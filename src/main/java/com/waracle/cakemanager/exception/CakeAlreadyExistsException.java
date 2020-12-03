package com.waracle.cakemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CakeAlreadyExistsException extends Exception {
	
	private static final long serialVersionUID = 2253790557576285596L;

	public CakeAlreadyExistsException(String message) {
		super();
	}
	
}
