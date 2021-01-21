package org.rfc.material.dto;

import java.io.Serializable;
import java.util.List;

public class BAPITableDTO extends BAPIDataType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<FieldValueDTO>[] rows;
	
	public BAPITableDTO() {
		super();
	}
	
	public BAPITableDTO(String name) {
		super(name);
	}
	
	public BAPITableDTO(String name,List<FieldValueDTO>[] rows) {
		super(name);
		this.rows=rows;
	}

	public List<FieldValueDTO>[] getRows() {
		return rows;
	}

	public void setRows(List<FieldValueDTO>[] rows) {
		this.rows = rows;
	}

}
