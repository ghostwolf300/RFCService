package org.rfc.material.dto;

import java.io.Serializable;

public class TaxClassificationsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FieldValueDTO depCountry;
	private FieldValueDTO taxType1;
	private FieldValueDTO taxClass1;
	
	public TaxClassificationsDTO() {
		super();
	}

	public FieldValueDTO getDepCountry() {
		return depCountry;
	}

	public void setDepCountry(FieldValueDTO depCountry) {
		this.depCountry = depCountry;
	}

	public FieldValueDTO getTaxType1() {
		return taxType1;
	}

	public void setTaxType1(FieldValueDTO taxType1) {
		this.taxType1 = taxType1;
	}

	public FieldValueDTO getTaxClass1() {
		return taxClass1;
	}

	public void setTaxClass1(FieldValueDTO taxClass1) {
		this.taxClass1 = taxClass1;
	}
	
}
