package org.rfc.material.rundata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.rfc.material.dto.HeaderColumnDTO;
import org.rfc.material.dto.RunDataDTO;
import org.rfc.material.dto.RunMaterialDTO;

@Entity
@Table(name="t_run_data")
@NamedNativeQuery(
		name="RunMaterials",
		query="select :runId as run_id,value as material,row_num from t_run_data where run_id=:runId and field_index=0 group by material order by row_num asc",
		resultSetMapping="RunMaterialsResult"
)
@SqlResultSetMapping(
		name="RunMaterialsResult",
		classes=@ConstructorResult(
				targetClass=RunMaterialDTO.class,
				columns= {
						@ColumnResult(name="run_id"),
						@ColumnResult(name="material"),
						@ColumnResult(name="row_num")
				}
		)
)
public class RunData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private RunDataKey id;
	@Column(name="value")
	private String value;
	
	public RunData() {
		super();
	}
	
	public RunData(int runId,int rowNumber,int fieldIndex,String value){
		id=new RunDataKey(runId,rowNumber,fieldIndex);
		this.value=value;
	}
	
	public RunData(RunDataDTO dto) {
		id=new RunDataKey(dto.getRunId(),dto.getRowNumber(),dto.getFieldIndex());
		this.value=dto.getValue();
	}
	
	public RunDataKey getId() {
		return id;
	}

	public void setId(RunDataKey id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
