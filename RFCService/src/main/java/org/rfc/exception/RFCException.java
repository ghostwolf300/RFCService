package org.rfc.exception;



public class RFCException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code=-1;
	
	public RFCException(int code,String message) {
		super(message);
		this.code=code;
	}
	
	public RFCException(int code,String message,Throwable err) {
		super(message,err);
		this.code=code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	

}
