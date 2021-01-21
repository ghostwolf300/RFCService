package org.rfc.material.dto;

import java.io.Serializable;
import java.util.List;

public class BAPIStructureDTO extends BAPIDataType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<FieldValueDTO> fields=null;
	
	public BAPIStructureDTO() {
		super();
	}
	
	public BAPIStructureDTO(String name) {
		super(name);
	}
	
	public BAPIStructureDTO(String name,List<FieldValueDTO> fields) {
		super(name);
		this.fields=fields;
	}

	public List<FieldValueDTO> getFields() {
		return fields;
	}

	public void setFields(List<FieldValueDTO> fields) {
		this.fields = fields;
	}

}
