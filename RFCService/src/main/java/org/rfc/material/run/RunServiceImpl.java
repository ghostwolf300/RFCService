package org.rfc.material.run;

import java.util.Optional;

import org.rfc.material.dto.RunDTO;
import org.rfc.material.runmaterial.RunMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("runService")
public class RunServiceImpl implements RunService {
	
	@Autowired
	private RunRepository runRepo;
	
	@Autowired
	private RunMaterialRepository runMaterialRepo;
	
	@Override
	public RunDTO getRun(int runId) {
		Optional<Run> opt=runRepo.findById(runId);
		RunDTO dto=null;
		if(opt.isPresent()) {
			Run run=opt.get();
			dto=new RunDTO(run);
		}
		return dto;
	}

	@Override
	public RunDTO resetRun(int runId) {
		//set all material status to 0
		runRepo.resetCounters(runId);
		//reset run counters
		runMaterialRepo.resetStatus(runId, 0);
		RunDTO dto=this.getRun(runId);
		return dto;
	}

}
