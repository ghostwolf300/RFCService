package org.rfc.material.dto;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.rfc.material.template.TemplateValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FieldValueDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
	
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
	
	@JsonIgnore
	public int getIntValue() {
		int intVal=0;
		if(value!=null && !value.isEmpty()) {
			intVal=Integer.parseInt(value);
		}
		return intVal;
	}
	
	@JsonIgnore
	public double getDoubleValue() {
		double doubleVal=0.0;
		if(value!=null && !value.isEmpty()) {
			try {
				doubleVal=nf.parse(value).doubleValue();
			} 
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doubleVal;
	}

}
