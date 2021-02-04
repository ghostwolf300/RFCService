package org.rfc.material.template;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.rfc.material.dto.FieldValueDTO;

@Entity
@Table(name="t_material_template_values")
public class TemplateValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private TemplateValueKey key;
	@Column(name="input_type")
	private int inputType;
	@Column(name="field_index")
	private int fieldIndex;
	@Column(name="constant_value")
	private String constantValue;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="template_id",nullable=false,insertable=false,updatable=false)
	private Template template;
	
	public TemplateValue() {
		super();
	}
	
	public TemplateValue(int templateId,String babiStructure,int rowId,FieldValueDTO fieldValue) {
		key=new TemplateValueKey(templateId,babiStructure,rowId,fieldValue.getField());
		inputType=(fieldValue.getValueType().equals("FIELD") ? 1 : 2);
		if(inputType==1) {
			fieldIndex=Integer.parseInt((fieldValue.getValue()==null ? "0" : fieldValue.getValue()));
		}
		else {
			constantValue=fieldValue.getValue();
		}
	}

	public TemplateValueKey getKey() {
		return key;
	}

	public void setKey(TemplateValueKey key) {
		this.key = key;
	}

	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public int getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(int fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	public String getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(String constantValue) {
		this.constantValue = constantValue;
	}
	
}
