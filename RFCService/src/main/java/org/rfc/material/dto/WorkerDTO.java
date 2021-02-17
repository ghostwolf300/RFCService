package org.rfc.material.dto;

import java.io.Serializable;

import org.rfc.material.worker.WorkerStatus;

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
	private WorkerStatus currentStatus;
	
	public WorkerDTO() {
		super();
	}

	public WorkerDTO(int id, int materialCount, int successCount, int errorCount,WorkerStatus status) {
		super();
		this.id = id;
		this.materialCount = materialCount;
		this.successCount = successCount;
		this.errorCount = errorCount;
		this.currentStatus=status;
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
	
	public WorkerStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(WorkerStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	@JsonProperty(value="progress",access=JsonProperty.Access.READ_ONLY)
	public int getProgress() {
		int progress=0;
		if(materialCount>0) {
			progress=(int) Math.round(((double)(successCount+errorCount)/(double)(materialCount))*100);
		}
		return progress;
	};
	
	
	public void addSuccess() {
		successCount++;
	}
	
	public void addError() {
		errorCount++;
	}

}
