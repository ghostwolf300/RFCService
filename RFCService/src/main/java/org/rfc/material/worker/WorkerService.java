package org.rfc.material.worker;

import java.util.List;

import org.rfc.material.dto.WorkerDTO;

public interface WorkerService {
	
	public List<WorkerDTO> createWorkers(int runId,int maxMaterials);

}
