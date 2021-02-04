package org.rfc.material.dto;

import java.io.Serializable;
import java.util.List;

public class RunDataWrapperDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<RunDataDTO> runDataList;
	
	public RunDataWrapperDTO() {
		super();
	}

	public List<RunDataDTO> getRunDataList() {
		return runDataList;
	}

	public void setRunDataList(List<RunDataDTO> runDataList) {
		this.runDataList = runDataList;
	}

}
