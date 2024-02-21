package com.devPontes.WebService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequiredObjectIsNullException() {
		super("Its is not allowed to persist a null object");
	}

	public RequiredObjectIsNullException(String msg) {
		super(msg);
	}

}
