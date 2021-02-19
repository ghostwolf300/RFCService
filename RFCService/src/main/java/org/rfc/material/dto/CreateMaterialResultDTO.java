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
	private int workerId;
	private List<ReturnMessageDTO> messages;
	
	public CreateMaterialResultDTO(int runId) {
		super();
		this.runId=runId;
	}
	
	public CreateMaterialResultDTO(int runId,String material,int workerId, List<ReturnMessageDTO> messages) {
		super();
		this.runId=runId;
		this.material = material;
		this.workerId = workerId;
		this.messages = messages;
		this.setStatusBasedOnMessages();
	}
	
	public CreateMaterialResultDTO(int runId,String material, int status, int workerId, List<ReturnMessageDTO> messages) {
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
	public int getWorkerId() {
		return workerId;
	}
	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}
	public List<ReturnMessageDTO> getMessages() {
		return messages;
	}
	public void setMessages(List<ReturnMessageDTO> messages) {
		this.messages = messages;
	}
	
	private void setStatusBasedOnMessages() {
		
		boolean success=false;
		boolean warning=false;
		boolean error=false;
		
		for(ReturnMessageDTO msg : messages) {
			switch(msg.getType()) {
			case "S" :
				success=true;
				break;
			case "W" :
				warning=true;
				break;
			case "E" :
				error=true;
				break;
			default	:
				error=true;
			}
		}
		if(success && !warning && !error) {
			status=1;
		}
		else if(warning && !error) {
			status=2;
		}
		else {
			status=3;
		}
	}
	
	
	
	

}