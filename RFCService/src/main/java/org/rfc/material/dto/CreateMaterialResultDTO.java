package org.rfc.material.dto;

import java.io.Serializable;
import java.util.List;

public class CreateMaterialResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int runId;
	private String material;
	private int status;
	private String workerId;
	private List<ReturnMessageDTO> messages;
	
	public CreateMaterialResultDTO(int runId) {
		super();
		this.runId=runId;
	}
	
	public CreateMaterialResultDTO(int runId,String material, int status, String workerId, List<ReturnMessageDTO> messages) {
		super();
		this.runId=runId;
		this.material = material;
		this.status = status;
		this.workerId = workerId;
		this.messages = messages;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public List<ReturnMessageDTO> getMessages() {
		return messages;
	}
	public void setMessages(List<ReturnMessageDTO> messages) {
		this.messages = messages;
	}
	
	
	
	

}
