package io.agw;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{

	private static final long serialVersionUID = -1494385262223322428L;
	
	private int httpStatusCode;
	private HttpStatus httpStatus;
	private String recommendedAction;
	private String details;

    public BaseException(int httpStatusCode, HttpStatus httpStatus, String message, String recommendedAction, String details) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.recommendedAction = recommendedAction;
        this.details = details;
    }

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getRecommendedAction() {
		return recommendedAction;
	}

	public void setRecommendedAction(String recommendedAction) {
		this.recommendedAction = recommendedAction;
	}


	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}
