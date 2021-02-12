package org.rfc.material.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.rfc.material.Material;
import org.rfc.material.MaterialRepository;
import org.rfc.material.dto.WorkerDTO;
import org.rfc.material.dto.WorkerResultDTO;
import org.rfc.material.runmaterial.RunMaterial;
import org.rfc.material.runmaterial.RunMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("workerService")
public class WorkerServiceImpl implements WorkerService {
	
	private final ExecutorService executor=Executors.newCachedThreadPool();
	private Map<Integer,Callable<WorkerResultDTO>> workerMap=null;
	private int workerCount=0;
	private ResultHandler resultHandler=null;
	
	private final static String STRUCTURES[]= {
			"HEADDATA",
			"CLIENTDATA",
			"MATERIALDESCRIPTION",
			"UNITSOFMEASURE",
			"SALESDATA",
			"TAXCLASSIFICATIONS",
			"PLANTDATA",
			"VALUATIONDATA",
			"STORAGELOCATIONDATA",
			"FORECASTPARAMETERS"
		};
	
	@Autowired
	private RunMaterialRepository runMaterialRepo;
	
	@Autowired
	private MaterialRepository materialRepo;
	
	@Override
	public List<WorkerDTO> createWorkers(int runId, int maxMaterials) {
		List<RunMaterial> runMaterials=runMaterialRepo.findByIdRunIdAndStatus(runId, 0);
		
		System.out.println("No# run materials: "+runMaterials.size());
		
		List<Material> materials=materialRepo.findByRunId(runId);
		System.out.println("Retrieved material count: "+materials.size());
		
		if(resultHandler==null) {
			resultHandler=new ResultHandler();
			executor.submit(resultHandler);
		}
		List<WorkerDTO> dtos=addWorkers(materials,maxMaterials,runId);
		return dtos;
	}
	
	private List<WorkerDTO> addWorkers(List<Material> materials,int maxMaterials,int runId){
		if(workerMap==null) {
			initWorkers();
		}
		List<WorkerDTO> dtos=new ArrayList<WorkerDTO>();
		List<List<Material>> splitMaterials=slice(materials,maxMaterials);
		int id;
		for(List<Material> workerMaterials : splitMaterials) {
			id=workerCount+1;
			CreateMaterialWorker worker=new CreateMaterialWorker(id,runId,workerMaterials,resultHandler);
			workerMap.put(worker.getId(), worker);
			workerCount++;
			dtos.add(worker.getDto());
			System.out.println(worker.getId()+"\t"+workerMaterials.size());
		}
		return dtos;
	}
	
	private void initWorkers() {
		workerMap=new HashMap<Integer,Callable<WorkerResultDTO>>();
		workerCount=0;
		resultHandler=new ResultHandler();
		executor.submit(resultHandler);
	}
	
	private List<List<Material>> slice(List<Material> materials, int maxRows){
		List<List<Material>> slicedList=new ArrayList<List<Material>>();
		List<Material> slice=new ArrayList<Material>();
		int counter=0;
		for(Material m : materials) {
			if(counter % maxRows==0 && counter>0) {
				slicedList.add(slice);
				slice=new ArrayList<Material>();
			}
			slice.add(m);
			counter++;
		}
		if(slice.size()>0) {
			slicedList.add(slice);
		}
		return slicedList;
	}

	@Override
	public void startOne(int workerId) {
		// TODO Auto-generated method stub
		Future<WorkerResultDTO> result=executor.submit(workerMap.get(workerId));
	}

	@Override
	public void startAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopOne(int workerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<WorkerDTO> getActiveWorkers(int runId) {
		List<WorkerDTO> dtos=new ArrayList<WorkerDTO>();
		if(workerMap!=null) {
			for(Integer workerId : workerMap.keySet()) {
				CreateMaterialWorker worker=(CreateMaterialWorker) workerMap.get(workerId);
				if(worker.getRunId()==runId) {
					dtos.add(worker.getDto());
				}
			}
		}
		return dtos;
	}

}
