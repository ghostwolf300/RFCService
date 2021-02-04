package org.rfc.material.rundata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RunDataKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="run_id")
	private int runId;
	@Column(name="row_num")
	private int rowNumber;
	@Column(name="field_index")
	private int fieldIndex;
	
	public RunDataKey() {
		super();
	}
	
	public RunDataKey(int runId, int rowNumber,int fieldIndex) {
		this.runId=runId;
		this.rowNumber=rowNumber;
		this.fieldIndex=fieldIndex;
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

	public int getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(int fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fieldIndex;
		result = prime * result + rowNumber;
		result = prime * result + runId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunDataKey other = (RunDataKey) obj;
		if (fieldIndex != other.fieldIndex)
			return false;
		if (rowNumber != other.rowNumber)
			return false;
		if (runId != other.runId)
			return false;
		return true;
	}
	
}
