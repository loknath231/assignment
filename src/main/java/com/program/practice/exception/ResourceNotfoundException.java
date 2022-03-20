package com.program.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotfoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResourceNotfoundException(String message) {
		super(message);
	}
}
