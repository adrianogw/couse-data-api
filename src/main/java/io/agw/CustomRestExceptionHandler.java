package io.agw;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler{

	private ResponseEntity<ExceptionResponse> buildResponseEntity(BaseException ex) {
		
		ExceptionResponse response = new ExceptionResponse(ex.getHttpStatus(), ex.getMessage(), ex.getDetails());

        return new ResponseEntity<ExceptionResponse>(response, response.getHttpStatus());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {

		return buildResponseEntity(ex);
    }
	
	
}
