package org.rfc.exception;

import java.io.Serializable;

public class ExceptionDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String message;
	
	public ExceptionDTO() {
		super();
	}
	
	public ExceptionDTO(int code, String message) {
		super();
		this.code=code;
		this.message=message;
	}
	
	public ExceptionDTO(RFCException e) {
		super();
		this.code=e.getCode();
		this.message=e.getMessage();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
