package io.agw;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
	
	private static final int httpStatusCode = 400;
	private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
	
	private static final long serialVersionUID = -4523889486103064019L;

	public ResourceNotFoundException(String message) {
		this(message, null, null);
	}

	public ResourceNotFoundException(String message, String recommendedAction, String details) {
		super(httpStatusCode, httpStatus, message, recommendedAction, details);
	}
	
}
