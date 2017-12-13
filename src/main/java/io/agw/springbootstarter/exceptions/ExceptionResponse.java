package io.agw.springbootstarter.exceptions;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class ExceptionResponse implements Serializable{
	 
	private static final long serialVersionUID = -2015480717094855076L;
	
	private HttpStatus httpStatus;
	private String message;
	private String recommendedAction;
	private String details;
 
    public ExceptionResponse(HttpStatus httpStatus, String message, String recommendedAction, String details) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
        this.recommendedAction = recommendedAction;
        this.details = details;
    }
 
    public ExceptionResponse(BaseException ex) {
        this(ex.getHttpStatus(), ex.getMessage(), ex.getRecommendedAction(), ex.getDetails());
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

	public String getRecommendedAction() {
		return recommendedAction;
	}

	public void setRecommendedAction(String recommendedAction) {
		this.recommendedAction = recommendedAction;
	}

    
}