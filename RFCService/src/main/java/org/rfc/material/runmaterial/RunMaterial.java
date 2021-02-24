package org.rfc.material.runmaterial;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.rfc.material.dto.RunMaterialDTO;
import org.rfc.material.messages.ReturnMessage;
import org.rfc.material.run.Run;

@Entity
@Table(name="t_run_material")
public class RunMaterial implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private RunMaterialKey id;
	@Column(name="row_num")
	private int rowNumber;
	@Column(name="status")
	private int status;
	@Column(name="updated_ts")
	private Timestamp updatedTs;
	
	//Added this to create relationship
	@MapsId("runId")
	@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="run_id",referencedColumnName="id")
	private Run run;
	
	@OneToMany(
		mappedBy="runMaterial",
		fetch=FetchType.LAZY,
		cascade=CascadeType.ALL,
		orphanRemoval=true
	)
	List<ReturnMessage> returnMessages;
	
	public RunMaterial() {
		super();
	}
	
	public RunMaterial(int runId,String material,int rowNumber,int status,Timestamp updatedTs) {
		id=new RunMaterialKey(runId,material);
		this.rowNumber=rowNumber;
		this.status=status;
		this.updatedTs=updatedTs;
	}
	
	public RunMaterial(Run run,String material,int rowNumber,int status,Timestamp updatedTs) {
		id=new RunMaterialKey(run.getId(),material);
		this.rowNumber=rowNumber;
		this.status=status;
		this.updatedTs=updatedTs;
		this.run=run;
	}
	
	public RunMaterial(RunMaterialDTO dto) {
		id=new RunMaterialKey(dto.getRunId(),dto.getMaterial());
		this.rowNumber=dto.getRowNumber();
		this.status=dto.getStatus();
		this.updatedTs=dto.getUpdatedTs();
	}

	public RunMaterialKey getId() {
		return id;
	}

	public void setId(RunMaterialKey id) {
		this.id = id;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Timestamp updatedTs) {
		this.updatedTs = updatedTs;
	}

	public Run getRun() {
		return run;
	}

	public void setRun(Run run) {
		this.run = run;
	}

	public List<ReturnMessage> getReturnMessages() {
		return returnMessages;
	}

	public void setReturnMessages(List<ReturnMessage> returnMessages) {
		this.returnMessages = returnMessages;
	}
	
}
