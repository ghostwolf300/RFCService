package org.rfc.po;

import java.io.Serializable;
import java.util.List;

public class ResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long poNumber=-1;
	private int statuCode;
	private String message;
	private String metaData;
	private List<ResponseLineDTO> lines;
	private boolean test;
	
	public ResponseDTO() {
		super();
	}

	public long getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(long poNumber) {
		this.poNumber = poNumber;
	}

	public int getStatuCode() {
		return statuCode;
	}

	public void setStatuCode(int statuCode) {
		this.statuCode = statuCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public List<ResponseLineDTO> getLines() {
		return lines;
	}

	public void setLines(List<ResponseLineDTO> lines) {
		this.lines = lines;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}
	

}
