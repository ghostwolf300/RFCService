package org.rfc.material;

public class BAPIField {
	
	private String fieldName;
	private String propertyName;
	private int length;
	private String type;
	private String info;
	private boolean mandatory;
	
	public BAPIField() {
		super();
	}
	
	public BAPIField(String fieldName,String propertyName) {
		super();
		this.fieldName=fieldName;
		this.propertyName=propertyName;
	}
	
	public BAPIField(String fieldName,String propertyName,int length) {
		super();
		this.fieldName=fieldName;
		this.propertyName=propertyName;
		this.length=length;
	}
	
	public BAPIField(String fieldName,String propertyName,int length,String type) {
		super();
		this.fieldName=fieldName;
		this.propertyName=propertyName;
		this.length=length;
		this.type=type;
	}
	
	public BAPIField(String fieldName,String propertyName,int length,String type,boolean mandatory) {
		super();
		this.fieldName=fieldName;
		this.propertyName=propertyName;
		this.length=length;
		this.type=type;
		this.mandatory=mandatory;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
}
