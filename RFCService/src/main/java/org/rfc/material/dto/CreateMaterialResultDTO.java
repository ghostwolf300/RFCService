package org.rfc.material.dto;

import java.io.Serializable;
import java.util.List;

public class CreateMaterialResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String material;
	private int status;
	private String workerId;
	private List<ReturnMessageDTO> messages;
	
	public CreateMaterialResultDTO() {
		super();
	}
	
	public CreateMaterialResultDTO(String material, int status, String workerId, List<ReturnMessageDTO> messages) {
		super();
		this.material = material;
		this.status = status;
		this.workerId = workerId;
		this.messages = messages;
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
