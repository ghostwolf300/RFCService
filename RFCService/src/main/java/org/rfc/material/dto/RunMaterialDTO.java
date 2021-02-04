package org.rfc.material.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import org.rfc.material.runmaterial.RunMaterial;

public class RunMaterialDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int runId;
	private String material;
	private int rowNumber;
	private int status;
	private Timestamp updatedTs;
	
	public RunMaterialDTO() {
		super();
	}
	
	public RunMaterialDTO(RunMaterial runMaterial) {
		this.runId=runMaterial.getId().getRunId();
		this.material=runMaterial.getId().getMaterial();
		this.rowNumber=runMaterial.getRowNumber();
		this.status=runMaterial.getStatus();
		this.updatedTs=runMaterial.getUpdatedTs();
	}
	
	public RunMaterialDTO(int runId,String material,int rowNumber) {
		this.runId=runId;
		this.material=material;
		this.rowNumber=rowNumber;
		this.status=0;
		this.updatedTs=new Timestamp(System.currentTimeMillis());
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Timestamp updatedTs) {
		this.updatedTs = updatedTs;
	}
	

}
