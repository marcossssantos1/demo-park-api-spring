package com.marcos.demoparkapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request, BindingResult bindingResult) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "invalid fields", bindingResult));
	}
	
	@ExceptionHandler(UserIdEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> userIdEntityNotFoundException(RuntimeException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

	@ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> invalidException(RuntimeException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(request,HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

	@ExceptionHandler(UsernameUniqueViolationException.class)
	public ResponseEntity<ErrorMessage> usernameUniqueViolationException(RuntimeException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON).
                body(new ErrorMessage(request, HttpStatus.FORBIDDEN,
                        ex.getMessage()));
    }


}
