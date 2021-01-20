package org.rfc.material.dto;

import java.io.Serializable;

public class HeadDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FieldValueDTO material;
	private FieldValueDTO indSector;
	private FieldValueDTO matlType;
	
	public HeadDataDTO() {
		super();
	}

	public FieldValueDTO getMaterial() {
		return material;
	}

	public void setMaterial(FieldValueDTO material) {
		this.material = material;
	}

	public FieldValueDTO getIndSector() {
		return indSector;
	}

	public void setIndSector(FieldValueDTO indSector) {
		this.indSector = indSector;
	}

	public FieldValueDTO getMatlType() {
		return matlType;
	}

	public void setMatlType(FieldValueDTO matlType) {
		this.matlType = matlType;
	}

}
