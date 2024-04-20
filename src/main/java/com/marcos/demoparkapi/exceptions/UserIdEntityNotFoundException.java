package com.marcos.demoparkapi.exceptions;

public class UserIdEntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserIdEntityNotFoundException(String message) {
		super(message);
	}

}
