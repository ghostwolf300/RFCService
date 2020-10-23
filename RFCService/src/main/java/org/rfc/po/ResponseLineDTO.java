package org.rfc.po;

import java.io.Serializable;

public class ResponseLineDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String id;
	private int number;
	private int row;
	private String message;
	private String messageVar1;
	private String messageVar2;
	private String messageVar3;
	private String messageVar4;
	
	public ResponseLineDTO() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageVar1() {
		return messageVar1;
	}

	public void setMessageVar1(String messageVar1) {
		this.messageVar1 = messageVar1;
	}

	public String getMessageVar2() {
		return messageVar2;
	}

	public void setMessageVar2(String messageVar2) {
		this.messageVar2 = messageVar2;
	}

	public String getMessageVar3() {
		return messageVar3;
	}

	public void setMessageVar3(String messageVar3) {
		this.messageVar3 = messageVar3;
	}

	public String getMessageVar4() {
		return messageVar4;
	}

	public void setMessageVar4(String messageVar4) {
		this.messageVar4 = messageVar4;
	}
	

}
