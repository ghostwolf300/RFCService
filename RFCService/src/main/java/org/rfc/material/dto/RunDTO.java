package org.rfc.material.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import org.rfc.material.run.Run;
import org.rfc.material.run.RunStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int templateId;
	private String name;
	private Timestamp createdTs;
	private int materialCount;
	private int successCount;
	private int errorCount;
	private boolean testRun;
	private RunStatus currentStatus;
	private boolean executing;
	private int resultQueueSize;
	
	public RunDTO() {
		super();
	}
	
	public RunDTO(Run run) {
		this.id=run.getId();
		this.name=run.getName();
		this.createdTs=run.getCreatedTs();
		this.materialCount=run.getMaterialCount();
		this.successCount=run.getSuccessCount();
		this.errorCount=run.getErrorCount();
		this.testRun=run.isTestRun();
		this.templateId=run.getTemplateId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
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

	public void setErrorCount(int failureCount) {
		this.errorCount = failureCount;
	}

	public boolean isTestRun() {
		return testRun;
	}

	public void setTestRun(boolean testRun) {
		this.testRun = testRun;
	}
	
	public RunStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(RunStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public boolean isExecuting() {
		return executing;
	}

	public void setExecuting(boolean executing) {
		this.executing = executing;
	}

	@JsonProperty(value="progress",access=JsonProperty.Access.READ_ONLY)
	public int getProgress() {
		int progress=0;
		if(materialCount>0) {
			progress=(int) Math.round(((double)(successCount+errorCount)/(double)(materialCount))*100);
		}
		return progress;
	};
	
	@JsonProperty(value="progressCount",access=JsonProperty.Access.READ_ONLY)
	public int getProgressCount(){
		int progressCount=0;
		progressCount=successCount+errorCount;
		return progressCount;
	}
	
	@JsonProperty(value="noRunCount",access=JsonProperty.Access.READ_ONLY)
	public int getNoRunCount() {
		int noRunCount=0;
		noRunCount=materialCount-this.getProgressCount();
		return noRunCount;
	}

	public int getResultQueueSize() {
		return resultQueueSize;
	}

	public void setResultQueueSize(int resultQueueSize) {
		this.resultQueueSize = resultQueueSize;
	}

}
