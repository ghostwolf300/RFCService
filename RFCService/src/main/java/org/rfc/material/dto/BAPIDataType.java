package org.rfc.material.dto;

public abstract class BAPIDataType {

	protected String name;
	
	public BAPIDataType() {
		super();
	}
	
	public BAPIDataType(String name) {
		super();
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
