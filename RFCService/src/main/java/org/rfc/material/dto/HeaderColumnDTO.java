package org.rfc.material.dto;

import java.io.Serializable;

public class HeaderColumnDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String headerText;
	private int fieldIndex;
	
	public HeaderColumnDTO() {
		super();
	}

	public HeaderColumnDTO(String headerText, int fieldIndex) {
		super();
		this.headerText = headerText;
		this.fieldIndex = fieldIndex;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public int getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(int fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

}
