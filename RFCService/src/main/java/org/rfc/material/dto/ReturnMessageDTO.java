package org.rfc.material.dto;

import java.io.Serializable;

public class ReturnMessageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int runId;
	private int workerId;
	private String materialId;
	private String number;
	private String type;
	private String message;
	private String row;
	private String messageVariable1;
	private String messageVariable2;
	private String messageVariable3;
	private String messageVariable4;
	
	public ReturnMessageDTO() {
		super();
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getMessageVariable1() {
		return messageVariable1;
	}

	public void setMessageVariable1(String messageVariable1) {
		this.messageVariable1 = messageVariable1;
	}

	public String getMessageVariable2() {
		return messageVariable2;
	}

	public void setMessageVariable2(String messageVariable2) {
		this.messageVariable2 = messageVariable2;
	}

	public String getMessageVariable3() {
		return messageVariable3;
	}

	public void setMessageVariable3(String messageVariable3) {
		this.messageVariable3 = messageVariable3;
	}

	public String getMessageVariable4() {
		return messageVariable4;
	}

	public void setMessageVariable4(String messageVariable4) {
		this.messageVariable4 = messageVariable4;
	}

	@Override
	public String toString() {
		return "ReturnMessageDTO [runId=" + runId + ", workerId=" + workerId + ", materialId=" + materialId
				+ ", number=" + number + ", type=" + type + ", message=" + message + ", row=" + row
				+ ", messageVariable1=" + messageVariable1 + ", messageVariable2=" + messageVariable2
				+ ", messageVariable3=" + messageVariable3 + ", messageVariable4=" + messageVariable4 + "]";
	}
	

}
