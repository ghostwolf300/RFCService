package org.rfc.po;

import java.io.Serializable;
import java.util.List;

public class POItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int poId;
	private int item;
	private String material;
	private String plant;
	private String storageLocation;
	private double quantity;
	private String valuationType;
	private String taxCode;
	private List<POItemTextDTO> textLines;
	private PODeliveryAddressDTO address;
	private List<POConfirmationDTO> confirmations;
	
	public POItemDTO() {
		super();
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
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

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public List<POItemTextDTO> getTextLines() {
		return textLines;
	}

	public void setTextLines(List<POItemTextDTO> textLines) {
		this.textLines = textLines;
	}

	public PODeliveryAddressDTO getAddress() {
		return address;
	}

	public void setAddress(PODeliveryAddressDTO address) {
		this.address = address;
	}

	public List<POConfirmationDTO> getConfirmations() {
		return confirmations;
	}

	public void setConfirmation(List<POConfirmationDTO> confirmations) {
		this.confirmations = confirmations;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
