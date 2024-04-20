package com.marcos.demoparkapi.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorMessage {

	private String path;

	private String method;

	private int status;

	private String statusText;

	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL) //Verifica se esta null se estiver não é enviado o campo!
	private Map<String, String> errors;

	public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		this.statusText = status.getReasonPhrase();
		this.message = message;
	}

	public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult bindingResult) {
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		this.statusText = status.getReasonPhrase();
		this.message = message;
		addErros(bindingResult);
	}

	private void addErros(BindingResult bindingResult) {
		this.errors = new HashMap<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

	}

	public String getPath() {
		return path;
	}

	public String getMethod() {
		return method;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusText() {
		return statusText;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "ErrorMessage [path=" + path + ", method=" + method + ", status=" + status + ", statusText=" + statusText
				+ ", message=" + message + ", errors=" + errors + "]";
	}

}
