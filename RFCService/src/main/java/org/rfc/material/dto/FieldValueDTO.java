package org.rfc.material.dto;

import java.io.Serializable;

public class FieldValueDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String valueType;
	private String value;
	
	public FieldValueDTO() {
		super();
	}
	
	public FieldValueDTO(String valueType,String value) {
		super();
		this.valueType=valueType;
		this.value=value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
