package org.rfc.material.dto;

import java.io.Serializable;

public class RunDataRowDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int runId;
	private int rowNumber;
	private String[] rowData;
	
	public RunDataRowDTO() {
		super();
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String[] getRowData() {
		return rowData;
	}

	public void setRowData(String[] rowData) {
		this.rowData = rowData;
	}

	
}
