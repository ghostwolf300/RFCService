package org.rfc.material;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TemplateValueKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="bapi_structure")
	private String bapiStructure;
	@Column(name="template_id")
	private int templateId;
	@Column(name="row_id")
	private int rowId;
	@Column(name="bapi_field")
	private String bapiField;
	
	public TemplateValueKey() {
		super();
	}

	public TemplateValueKey(int templateId,String bapiStructure, int rowId, String bapiField) {
		super();
		this.bapiStructure = bapiStructure;
		this.templateId = templateId;
		this.rowId = rowId;
		this.bapiField = bapiField;
	}

	public String getBapiStructure() {
		return bapiStructure;
	}

	public void setBapiStructure(String bapiStructure) {
		this.bapiStructure = bapiStructure;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getBapiField() {
		return bapiField;
	}

	public void setBapiField(String babiField) {
		this.bapiField = babiField;
	}

}
