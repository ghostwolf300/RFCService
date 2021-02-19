package org.rfc.material.messages;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.rfc.material.dto.ReturnMessageDTO;
import org.rfc.material.runmaterial.RunMaterial;

@Entity
@Table(name="t_message")
public class ReturnMessage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="worker_id")
	private int workerId;
	@Column(name="num",length=3)
	private String number;
	@Column(name="type",length=1)
	private String type;
	@Column(name="message",length=128)
	private String message;
	@Column(name="row_num",length=3)
	private String rowNumber;
	@Column(name="msg_var_1",length=60)
	private String messageVariable1;
	@Column(name="msg_var_2",length=60)
	private String messageVariable2;
	@Column(name="msg_var_3",length=60)
	private String messageVariable3;
	@Column(name="msg_var_4",length=60)
	private String messageVariable4;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name="run_id",referencedColumnName="run_id"),
			@JoinColumn(name="material",referencedColumnName="material")
	})
	private RunMaterial runMaterial;
	
	public ReturnMessage() {
		super();
	}
	
	public ReturnMessage(ReturnMessageDTO msg,RunMaterial rm) {
		super();
		this.workerId=msg.getWorkerId();
		this.number=msg.getNumber();
		this.type=msg.getType();
		this.message=msg.getMessage();
		this.rowNumber=msg.getRow();
		this.messageVariable1=msg.getMessageVariable1();
		this.messageVariable2=msg.getMessageVariable2();
		this.messageVariable3=msg.getMessageVariable3();
		this.messageVariable4=msg.getMessageVariable4();
		this.runMaterial=rm;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
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

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
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

	public RunMaterial getRunMaterial() {
		return runMaterial;
	}

	public void setRunMaterial(RunMaterial runMaterial) {
		this.runMaterial = runMaterial;
	}

	
}
