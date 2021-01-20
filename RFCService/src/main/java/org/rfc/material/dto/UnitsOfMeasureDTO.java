package org.rfc.material.dto;

import java.io.Serializable;

public class UnitsOfMeasureDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FieldValueDTO length;
	private FieldValueDTO width;
	private FieldValueDTO height;
	private FieldValueDTO unitDim;
	private FieldValueDTO volumeUnit;
	private FieldValueDTO grossWt;
	private FieldValueDTO unitOfWt;
	
	public UnitsOfMeasureDTO() {
		super();
	}

	public FieldValueDTO getLength() {
		return length;
	}

	public void setLength(FieldValueDTO length) {
		this.length = length;
	}

	public FieldValueDTO getWidth() {
		return width;
	}

	public void setWidth(FieldValueDTO width) {
		this.width = width;
	}

	public FieldValueDTO getHeight() {
		return height;
	}

	public void setHeight(FieldValueDTO height) {
		this.height = height;
	}

	public FieldValueDTO getUnitDim() {
		return unitDim;
	}

	public void setUnitDim(FieldValueDTO unitDim) {
		this.unitDim = unitDim;
	}

	public FieldValueDTO getVolumeUnit() {
		return volumeUnit;
	}

	public void setVolumeUnit(FieldValueDTO volumeUnit) {
		this.volumeUnit = volumeUnit;
	}

	public FieldValueDTO getGrossWt() {
		return grossWt;
	}

	public void setGrossWt(FieldValueDTO grossWt) {
		this.grossWt = grossWt;
	}

	public FieldValueDTO getUnitOfWt() {
		return unitOfWt;
	}

	public void setUnitOfWt(FieldValueDTO unitOfWt) {
		this.unitOfWt = unitOfWt;
	}

}
