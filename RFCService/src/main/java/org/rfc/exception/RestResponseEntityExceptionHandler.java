package org.rfc.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.po.PORestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger logger = LogManager.getLogger(RestResponseEntityExceptionHandler.class);
	
	@ExceptionHandler(RFCException.class)
	protected ResponseEntity<Object> handleRFCException(RuntimeException ex,WebRequest request){
		ExceptionDTO responseBody=new ExceptionDTO((RFCException) ex);
		logger.error(responseBody.getCode()+" : "+responseBody.getMessage());
		return new ResponseEntity<>(responseBody,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
