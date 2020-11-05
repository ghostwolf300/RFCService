package org.rfc.orderconf;

import java.io.Serializable;
import java.sql.Date;

public class SupplierConfirmationItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int itemNumber;
	private double orderedQuantity;
	private Date confirmedDate;
	private String supplierId;
	private String supplierDescription;
	private String customerId;
	private String customerDescription;
	private String uom;
	private double unitNetPrice;
	private double discountPercentage;
	private double totalNetPrice;
	private double grossPrice;
	
	public SupplierConfirmationItemDTO() {
		super();
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public double getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(double orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public Date getConfirmedDate() {
		return confirmedDate;
	}

	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierDescription() {
		return supplierDescription;
	}

	public void setSupplierDescription(String supplierDescription) {
		this.supplierDescription = supplierDescription;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerDescription() {
		return customerDescription;
	}

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public double getUnitNetPrice() {
		return unitNetPrice;
	}

	public void setUnitNetPrice(double unitPrice) {
		this.unitNetPrice = unitPrice;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public double getTotalNetPrice() {
		return totalNetPrice;
	}

	public void setTotalNetPrice(double netPrice) {
		this.totalNetPrice = netPrice;
	}

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

}
