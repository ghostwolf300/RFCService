package org.rfc.material.dto;

import java.io.Serializable;
import java.util.List;

public class RunDataWrapperDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int templateId;
	private int runId;
	private List<RunDataRowDTO> runDataList;
	
	public RunDataWrapperDTO() {
		super();
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public List<RunDataRowDTO> getRunDataList() {
		return runDataList;
	}

	public void setRunDataList(List<RunDataRowDTO> runDataList) {
		this.runDataList = runDataList;
	}

}
