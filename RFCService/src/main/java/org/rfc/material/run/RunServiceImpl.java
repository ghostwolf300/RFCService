package org.rfc.material.run;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.material.dto.RunDTO;
import org.rfc.material.messages.ReturnMessageRepository;
import org.rfc.material.runmaterial.RunMaterialRepository;
import org.rfc.material.worker.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("runService")
public class RunServiceImpl implements RunService {
	
	private static final Logger logger=LogManager.getLogger(RunService.class);
	
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
		logger.log(Level.INFO,"resetRun runId: "+runId+"\tReset run.");
		//This also stops running workers first
		workerService.removeAll(runId);
		logger.log(Level.DEBUG,"resetRun runId: "+runId+"\tWaiting results to be stored in db.");
		int queueSize;
		while((queueSize=workerService.getResultQueueSize())>0) {
			logger.log(Level.DEBUG,"resetRun runId: "+runId+"\tResult queue size: "+queueSize+". Waiting 5s.");
			try {
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) {
				logger.log(Level.DEBUG,"resetRun runId: "+runId+"\tResult queue wait interrupted!");
			}
		}
		logger.log(Level.DEBUG,"resetRun runId: "+runId+"\tReseting counters.");
		//set all material status to 0
		runRepo.resetCounters(runId);
		//reset run counters
		runMaterialRepo.resetStatus(runId, 0);
		//clear messages
		logger.log(Level.DEBUG,"resetRun runId: "+runId+"\tRemoving messages.");
		messageRepo.deleteByRunMaterialIdRunId(runId);
		RunDTO dto=this.getRun(runId);
		return dto;
	}

}
