package org.rfc.material.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int materialCount;
	private int successCount;
	private int errorCount;
	private int status;
	
	public WorkerDTO() {
		super();
	}

	public WorkerDTO(int id, int materialCount, int successCount, int errorCount, int status) {
		super();
		this.id = id;
		this.materialCount = materialCount;
		this.successCount = successCount;
		this.errorCount = errorCount;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaterialCount() {
		return materialCount;
	}

	public void setMaterialCount(int materialCount) {
		this.materialCount = materialCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@JsonProperty(value="progress",access=JsonProperty.Access.READ_ONLY)
	public int getProgress() {
		int progress=0;
		if(materialCount>0) {
			progress=(successCount+errorCount)/materialCount;
		}
		//progress=25;
		return progress;
	};
	
	
	

}
