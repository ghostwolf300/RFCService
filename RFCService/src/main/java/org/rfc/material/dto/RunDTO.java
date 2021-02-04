package org.rfc.material.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import org.rfc.material.run.Run;

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

}
