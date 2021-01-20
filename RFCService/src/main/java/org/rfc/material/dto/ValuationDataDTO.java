package org.rfc.material.dto;

import java.io.Serializable;

public class ValuationDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FieldValueDTO valArea;
	private FieldValueDTO priceCtrl;
	private FieldValueDTO valClass;
	private FieldValueDTO movingPr;
	private FieldValueDTO stdPrice;
	private FieldValueDTO priceUnit;
	private FieldValueDTO qtyStruct;
	private FieldValueDTO origMat;
	
	public ValuationDataDTO() {
		super();
	}

	public FieldValueDTO getValArea() {
		return valArea;
	}

	public void setValArea(FieldValueDTO valArea) {
		this.valArea = valArea;
	}

	public FieldValueDTO getPriceCtrl() {
		return priceCtrl;
	}

	public void setPriceCtrl(FieldValueDTO priceCtrl) {
		this.priceCtrl = priceCtrl;
	}

	public FieldValueDTO getValClass() {
		return valClass;
	}

	public void setValClass(FieldValueDTO valClass) {
		this.valClass = valClass;
	}

	public FieldValueDTO getMovingPr() {
		return movingPr;
	}

	public void setMovingPr(FieldValueDTO movingPr) {
		this.movingPr = movingPr;
	}

	public FieldValueDTO getStdPrice() {
		return stdPrice;
	}

	public void setStdPrice(FieldValueDTO stdPrice) {
		this.stdPrice = stdPrice;
	}

	public FieldValueDTO getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(FieldValueDTO priceUnit) {
		this.priceUnit = priceUnit;
	}

	public FieldValueDTO getQtyStruct() {
		return qtyStruct;
	}

	public void setQtyStruct(FieldValueDTO qtyStruct) {
		this.qtyStruct = qtyStruct;
	}

	public FieldValueDTO getOrigMat() {
		return origMat;
	}

	public void setOrigMat(FieldValueDTO origMat) {
		this.origMat = origMat;
	}

}
