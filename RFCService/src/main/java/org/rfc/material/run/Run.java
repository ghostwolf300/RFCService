package org.rfc.material.run;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.rfc.material.dto.RunDTO;

@Entity
@Table(name="t_run")
public class Run implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="created_ts")
	private Timestamp createdTs;
	@Column(name="material_count")
	private int materialCount;
	@Column(name="success_count")
	private int successCount;
	@Column(name="error_count")
	private int errorCount;
	@Column(name="test_run")
	private boolean testRun;
	@Column(name="template_id")
	private int templateId;
	
	public Run() {
		super();
	}
	
	public Run(RunDTO run) {
		if(run.getId()!=-1) {
			this.id=run.getId();
			this.createdTs=run.getCreatedTs();
		}
		else {
			this.id=null;
			this.createdTs=new Timestamp(System.currentTimeMillis());
		}
		this.name=run.getName();
		
		this.materialCount=run.getMaterialCount();
		this.successCount=run.getSuccessCount();
		this.errorCount=run.getErrorCount();
		this.testRun=run.isTestRun();
		this.templateId=run.getTemplateId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public int getMaterialCount() {
		return materialCount;
	}

	public void setMaterialCount(int materialCount) {
		this.materialCount = materialCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public boolean isTestRun() {
		return testRun;
	}

	public void setTestRun(boolean testRun) {
		this.testRun = testRun;
	}
}
