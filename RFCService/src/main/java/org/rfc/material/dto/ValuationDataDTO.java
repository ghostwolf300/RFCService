package org.rfc.material.dto;

import java.io.Serializable;

public class ValuationDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String valuationArea;
	private String priceControl;
	private String valuationClass;
	private double movingAveragePrice;
	private double standardPrice;
	private int priceUnit;
	private String costWithQtyStructure;
	private String materialRelatedOrigin;
	
	public ValuationDataDTO() {
		super();
	}

	public String getValuationArea() {
		return valuationArea;
	}

	public void setValuationArea(String valuationArea) {
		this.valuationArea = valuationArea;
	}

	public String getPriceControl() {
		return priceControl;
	}

	public void setPriceControl(String priceControl) {
		this.priceControl = priceControl;
	}

	public String getValuationClass() {
		return valuationClass;
	}

	public void setValuationClass(String valuationClass) {
		this.valuationClass = valuationClass;
	}

	public double getMovingAveragePrice() {
		return movingAveragePrice;
	}

	public void setMovingAveragePrice(double movingAveragePrice) {
		this.movingAveragePrice = movingAveragePrice;
	}

	public double getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(double standardPrice) {
		this.standardPrice = standardPrice;
	}

	public int getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(int priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getCostWithQtyStructure() {
		return costWithQtyStructure;
	}

	public void setCostWithQtyStructure(String costWithQtyStructure) {
		this.costWithQtyStructure = costWithQtyStructure;
	}

	public String getMaterialRelatedOrigin() {
		return materialRelatedOrigin;
	}

	public void setMaterialRelatedOrigin(String materialRelatedOrigin) {
		this.materialRelatedOrigin = materialRelatedOrigin;
	}
	

}
