package org.rfc.material.worker;

import java.util.List;

import org.rfc.material.dto.FeedLineDTO;
import org.rfc.material.dto.WorkerDTO;

public interface WorkerService {
	
	public List<WorkerDTO> createWorkers(int runId,int maxMaterials);
	public List<WorkerDTO> getActiveWorkers(int runId);
	public void startOne(int workerId);
	public List<WorkerDTO> startAll(int runId);
	public void pauseOne(int workerId);
	public void pauseAll(int runId);
	public void stopOne(int workerId);
	public List<WorkerDTO> stopAll(int runId);
	public boolean isExecuting(int runId);
	public int getResultQueueSize();
	public void removeAll(int runId);
	public List<FeedLineDTO> getFeedLines(int runId);

}
