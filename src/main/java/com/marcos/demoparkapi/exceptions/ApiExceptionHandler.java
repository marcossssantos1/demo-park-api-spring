package com.marcos.demoparkapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult bindingResult){
	        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body( new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "invalid fields", bindingResult));
	    }

}
