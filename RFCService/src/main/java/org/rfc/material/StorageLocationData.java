package org.rfc.material;

import java.io.Serializable;

public class StorageLocationData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String plant;
	public String storageLocation;
	
	public StorageLocationData() {
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
