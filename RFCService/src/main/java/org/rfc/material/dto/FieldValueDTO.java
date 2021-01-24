package org.rfc.material.dto;

import java.io.Serializable;

import org.rfc.material.TemplateValue;

public class FieldValueDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String field;
	private String valueType;
	private String value;
	
	public FieldValueDTO() {
		super();
	}
	
	public FieldValueDTO(String field,String valueType,String value) {
		super();
		this.field=field;
		this.valueType=valueType;
		this.value=value;
	}
	
	public FieldValueDTO(TemplateValue tv) {
		super();
		this.field=tv.getKey().getBapiField();
		if(tv.getInputType()==1) {
			this.valueType="FIELD";
			this.value=String.valueOf(tv.getFieldIndex());
		}
		else if(tv.getInputType()==2) {
			this.valueType="CONSTANT";
			this.value=tv.getConstantValue();
		}
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
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
