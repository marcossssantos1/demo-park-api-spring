package com.marcos.demoparkapi.exceptions;

public class PasswordInvalidException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public PasswordInvalidException(String message) {
        super(message);
	}

}
