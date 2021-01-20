package org.rfc.material.dto;

import java.io.Serializable;

public class SalesDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FieldValueDTO salesOrg;
	private FieldValueDTO distrChan;
	private FieldValueDTO matlStats;
	private FieldValueDTO itemCat;
	private FieldValueDTO acctAssgt;
	private FieldValueDTO delygPlnt;
	
	public SalesDataDTO() {
		super();
	}

	public FieldValueDTO getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(FieldValueDTO salesOrg) {
		this.salesOrg = salesOrg;
	}

	public FieldValueDTO getDistrChan() {
		return distrChan;
	}

	public void setDistrChan(FieldValueDTO distrChan) {
		this.distrChan = distrChan;
	}

	public FieldValueDTO getMatlStats() {
		return matlStats;
	}

	public void setMatlStats(FieldValueDTO matlStats) {
		this.matlStats = matlStats;
	}

	public FieldValueDTO getItemCat() {
		return itemCat;
	}

	public void setItemCat(FieldValueDTO itemCat) {
		this.itemCat = itemCat;
	}

	public FieldValueDTO getAcctAssgt() {
		return acctAssgt;
	}

	public void setAcctAssgt(FieldValueDTO acctAssgt) {
		this.acctAssgt = acctAssgt;
	}

	public FieldValueDTO getDelygPlnt() {
		return delygPlnt;
	}

	public void setDelygPlnt(FieldValueDTO delygPlnt) {
		this.delygPlnt = delygPlnt;
	}

}
