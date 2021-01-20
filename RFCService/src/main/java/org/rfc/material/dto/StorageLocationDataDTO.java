package org.rfc.material.dto;

import java.io.Serializable;

public class StorageLocationDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FieldValueDTO plant;
	private FieldValueDTO stgeLoc;
	
	public StorageLocationDataDTO() {
		super();
	}

	public FieldValueDTO getPlant() {
		return plant;
	}

	public void setPlant(FieldValueDTO plant) {
		this.plant = plant;
	}

	public FieldValueDTO getStgeLoc() {
		return stgeLoc;
	}

	public void setStgeLoc(FieldValueDTO stgeLoc) {
		this.stgeLoc = stgeLoc;
	}

}
