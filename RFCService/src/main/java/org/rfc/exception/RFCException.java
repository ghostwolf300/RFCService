package org.rfc.exception;


public class RFCException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code=-1;
	
	public RFCException(int code,String message) {
		super(message);
		this.code=code;
	}
	
	public RFCException(int code,String message,Throwable cause) {
		super(message,cause);
		this.code=code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	

}
