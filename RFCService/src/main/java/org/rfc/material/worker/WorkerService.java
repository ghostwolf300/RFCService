package org.rfc.material.worker;

import java.util.List;

import org.rfc.material.dto.WorkerDTO;

public interface WorkerService {
	
	public List<WorkerDTO> createWorkers(int runId,int maxMaterials);
	public List<WorkerDTO> getActiveWorkers(int runId);
	public void startOne(int workerId);
	public void startAll();
	public void stopOne(int workerId);
	public void stopAll();

}
