package org.rfc.material.run;

import java.util.Optional;

import org.rfc.material.dto.RunDTO;
import org.rfc.material.messages.ReturnMessageRepository;
import org.rfc.material.runmaterial.RunMaterialRepository;
import org.rfc.material.worker.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("runService")
public class RunServiceImpl implements RunService {
	
	@Autowired
	private WorkerService workerService;
	
	@Autowired
	private RunRepository runRepo;
	
	@Autowired
	private RunMaterialRepository runMaterialRepo;
	
	@Autowired
	private ReturnMessageRepository messageRepo;
	
	@Override
	public RunDTO getRun(int runId) {
		Optional<Run> opt=runRepo.findById(runId);
		RunDTO dto=null;
		if(opt.isPresent()) {
			Run run=opt.get();
			dto=new RunDTO(run);
			if(workerService.isExecuting(runId)) {
				dto.setExecuting(true);
			}
			else {
				dto.setExecuting(false);
			}
			dto.setResultQueueSize(workerService.getResultQueueSize());
		}
		return dto;
	}

	@Override
	public RunDTO resetRun(int runId) {
		
		workerService.stopAll(runId);
		int queueSize;
		while((queueSize=workerService.getResultQueueSize())>0) {
			System.out.println("Result queue size is "+queueSize+". Waiting 5s.");
			try {
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) {
				System.out.println("Result queue wait interrupted!");
			}
		}
		
		//set all material status to 0
		runRepo.resetCounters(runId);
		//reset run counters
		runMaterialRepo.resetStatus(runId, 0);
		//clear messages
		messageRepo.deleteByRunMaterialIdRunId(runId);
		RunDTO dto=this.getRun(runId);
		return dto;
	}

}
