package org.rfc.material.rundata;

import java.util.List;

import org.rfc.material.dto.RunMaterialDTO;

public interface RunDataRepositoryCustom {
	public List<RunMaterialDTO> getRunMaterials(int runId);
}
