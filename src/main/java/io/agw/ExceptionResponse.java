package io.agw;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class ExceptionResponse implements Serializable{
	 
	private static final long serialVersionUID = -2015480717094855076L;
	
	private HttpStatus httpStatus;
    private String message;
    private String details;
 
    public ExceptionResponse(HttpStatus httpStatus, String message, String details) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
        this.details = details;
    }
 
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

    
}