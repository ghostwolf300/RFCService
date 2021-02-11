package org.rfc.material.dto;

import java.io.Serializable;

public class StorageLocationDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String plant;
	public String storageLocation;
	
	public StorageLocationDataDTO() {
		super();
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	

}
