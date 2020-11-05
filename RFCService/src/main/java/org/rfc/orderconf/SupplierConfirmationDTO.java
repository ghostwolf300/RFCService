package org.rfc.orderconf;

import java.io.Serializable;
import java.util.List;

public class SupplierConfirmationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String supplierOrderId;
	private String customerOrderId;
	private double netPrice;
	private double grossPrice;
	private List<SupplierConfirmationItemDTO> items=null;
	
	public SupplierConfirmationDTO() {
		super();
	}

	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(String orderNumber) {
		this.supplierOrderId = orderNumber;
	}

	public String getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(String customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public double getTotalNetPrice() {
		return netPrice;
	}

	public void setTotalNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public List<SupplierConfirmationItemDTO> getItems() {
		return items;
	}

	public void setItems(List<SupplierConfirmationItemDTO> items) {
		this.items = items;
	}
	
	
}
