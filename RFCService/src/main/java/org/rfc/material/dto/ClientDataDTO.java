package org.rfc.material.dto;

import java.io.Serializable;

public class ClientDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FieldValueDTO matlGroup;
	private FieldValueDTO oldMatNo;
	private FieldValueDTO baseUom;
	private FieldValueDTO itemCat;
	private FieldValueDTO purStatus;
	private FieldValueDTO netWeight;
	private FieldValueDTO unitOfWt;
	private FieldValueDTO storConds;
	private FieldValueDTO purValKey;
	private FieldValueDTO transGrp;
	private FieldValueDTO labelType;
	private FieldValueDTO labelForm;
	
	public ClientDataDTO() {
		super();
	}

	public FieldValueDTO getMatlGroup() {
		return matlGroup;
	}

	public void setMatlGroup(FieldValueDTO matlGroup) {
		this.matlGroup = matlGroup;
	}

	public FieldValueDTO getOldMatNo() {
		return oldMatNo;
	}

	public void setOldMatNo(FieldValueDTO oldMatNo) {
		this.oldMatNo = oldMatNo;
	}

	public FieldValueDTO getBaseUom() {
		return baseUom;
	}

	public void setBaseUom(FieldValueDTO baseUom) {
		this.baseUom = baseUom;
	}

	public FieldValueDTO getItemCat() {
		return itemCat;
	}

	public void setItemCat(FieldValueDTO itemCat) {
		this.itemCat = itemCat;
	}

	public FieldValueDTO getPurStatus() {
		return purStatus;
	}

	public void setPurStatus(FieldValueDTO purStatus) {
		this.purStatus = purStatus;
	}

	public FieldValueDTO getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(FieldValueDTO netWeight) {
		this.netWeight = netWeight;
	}

	public FieldValueDTO getUnitOfWt() {
		return unitOfWt;
	}

	public void setUnitOfWt(FieldValueDTO unitOfWt) {
		this.unitOfWt = unitOfWt;
	}

	public FieldValueDTO getStorConds() {
		return storConds;
	}

	public void setStorConds(FieldValueDTO storConds) {
		this.storConds = storConds;
	}

	public FieldValueDTO getPurValKey() {
		return purValKey;
	}

	public void setPurValKey(FieldValueDTO purValKey) {
		this.purValKey = purValKey;
	}

	public FieldValueDTO getTransGrp() {
		return transGrp;
	}

	public void setTransGrp(FieldValueDTO transGrp) {
		this.transGrp = transGrp;
	}

	public FieldValueDTO getLabelType() {
		return labelType;
	}

	public void setLabelType(FieldValueDTO labelType) {
		this.labelType = labelType;
	}

	public FieldValueDTO getLabelForm() {
		return labelForm;
	}

	public void setLabelForm(FieldValueDTO labelForm) {
		this.labelForm = labelForm;
	}

	

}
