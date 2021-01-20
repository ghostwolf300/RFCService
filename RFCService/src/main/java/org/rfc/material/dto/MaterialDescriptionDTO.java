package org.rfc.material.dto;

import java.io.Serializable;

public class MaterialDescriptionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FieldValueDTO langu;
	private FieldValueDTO matlDesc;
	
	public MaterialDescriptionDTO() {
		super();
	}

	public FieldValueDTO getLangu() {
		return langu;
	}

	public void setLangu(FieldValueDTO langu) {
		this.langu = langu;
	}

	public FieldValueDTO getMatlDesc() {
		return matlDesc;
	}

	public void setMatlDesc(FieldValueDTO matlDesc) {
		this.matlDesc = matlDesc;
	}

}
