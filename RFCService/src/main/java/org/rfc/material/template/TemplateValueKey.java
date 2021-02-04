package org.rfc.material.template;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bapiField == null) ? 0 : bapiField.hashCode());
		result = prime * result + ((bapiStructure == null) ? 0 : bapiStructure.hashCode());
		result = prime * result + rowId;
		result = prime * result + templateId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemplateValueKey other = (TemplateValueKey) obj;
		if (bapiField == null) {
			if (other.bapiField != null)
				return false;
		} else if (!bapiField.equals(other.bapiField))
			return false;
		if (bapiStructure == null) {
			if (other.bapiStructure != null)
				return false;
		} else if (!bapiStructure.equals(other.bapiStructure))
			return false;
		if (rowId != other.rowId)
			return false;
		if (templateId != other.templateId)
			return false;
		return true;
	}

}
