package org.rfc.material.worker;

import java.util.ArrayList;
import java.util.List;

import org.rfc.material.dto.WorkerDTO;
import org.rfc.material.runmaterial.RunMaterial;
import org.rfc.material.runmaterial.RunMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("workerService")
public class WorkerServiceImpl implements WorkerService {
	
	@Autowired
	private RunMaterialRepository runMaterialRepo;
	
	@Override
	public List<WorkerDTO> createWorkers(int runId, int maxMaterials) {
		List<RunMaterial> materials=runMaterialRepo.findByIdRunIdAndStatus(runId, 0);
		System.out.println("No run materials: "+materials.size());
		return getTestWorkerList();
	}
	
	private List<WorkerDTO> getTestWorkerList() {
		List<WorkerDTO> workers=new ArrayList<WorkerDTO>();
		workers.add(new WorkerDTO(1,5000,0,0,0));
		workers.add(new WorkerDTO(2,5000,0,0,0));
		workers.add(new WorkerDTO(3,5000,0,0,0));
		workers.add(new WorkerDTO(4,5000,0,0,0));
		return workers;
	}

}
