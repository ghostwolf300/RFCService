package org.rfc.material.runmaterial;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RunMaterialKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="run_id")
	private int runId;
	@Column(name="material")
	private String material;
	
	public RunMaterialKey() {
		super();
	}
	
	public RunMaterialKey(int runId,String material) {
		this.runId=runId;
		this.material=material;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((material == null) ? 0 : material.hashCode());
		result = prime * result + runId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunMaterialKey other = (RunMaterialKey) obj;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (runId != other.runId)
			return false;
		return true;
	}
	
}
