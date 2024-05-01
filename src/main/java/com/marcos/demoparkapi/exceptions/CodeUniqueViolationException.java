package com.marcos.demoparkapi.exceptions;

public class CodeUniqueViolationException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public CodeUniqueViolationException(String message) {
        super(message);
    }

}
