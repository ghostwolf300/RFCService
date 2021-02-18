package org.rfc.material.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.rfc.material.Material;
import org.rfc.material.MaterialRepository;
import org.rfc.material.dto.CreateMaterialResultDTO;
import org.rfc.material.dto.WorkerDTO;
import org.rfc.material.dto.WorkerResultDTO;
import org.rfc.material.run.RunRepository;
import org.rfc.material.runmaterial.RunMaterial;
import org.rfc.material.runmaterial.RunMaterialRepository;
import org.rfc.sap.SapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.conn.jco.JCoDestination;

@Service("workerService")
public class WorkerServiceImpl implements WorkerService {
	
	private final ExecutorService resultExecutor=Executors.newSingleThreadExecutor();
	private final ExecutorService workerExecutor=Executors.newCachedThreadPool();
	private final BlockingQueue<CreateMaterialResultDTO> resultQueue=new LinkedBlockingQueue<CreateMaterialResultDTO>();
	private Map<Integer,Runnable> workerMap=null;
	private int workerCount=0;
	private ResultHandler resultHandler=null;
	
	@Autowired
	private SapService sapService;
	
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
	private RunRepository runRepo;
	
	@Autowired
	private RunMaterialRepository runMaterialRepo;
	
	@Autowired
	private MaterialRepository materialRepo;
	
	@Override
	public List<WorkerDTO> createWorkers(int runId, int maxMaterials) {
		
		clearWorkers(runId);
		
		List<RunMaterial> runMaterials=runMaterialRepo.findByIdRunIdAndStatus(runId, 0);
		
		System.out.println("No# run materials: "+runMaterials.size());
		
		List<Material> materials=materialRepo.findByRunId(runId);
		System.out.println("Retrieved material count: "+materials.size());
		
		
		List<WorkerDTO> dtos=addWorkers(materials,maxMaterials,runId);
		return dtos;
	}
	
	private void clearWorkers(int runId) {
		if(workerMap!=null) {
			stopAll(runId);
			List<WorkerDTO> dtos=getActiveWorkers(runId);
			for(WorkerDTO w : dtos) {
				workerMap.remove(w.getId());	
			}
		}
	}
	
	private List<WorkerDTO> addWorkers(List<Material> materials,int maxMaterials,int runId){
		if(workerMap==null) {
			initWorkers();
		}

		List<WorkerDTO> dtos=new ArrayList<WorkerDTO>();
		List<List<Material>> splitMaterials=slice(materials,maxMaterials);
		int id;
		JCoDestination destination=sapService.getDestination();
		for(List<Material> workerMaterials : splitMaterials) {
			id=workerCount+1;
			CreateMaterialWorker worker=new CreateMaterialWorker(id,runId,workerMaterials,resultQueue,destination);
			workerMap.put(worker.getId(), worker);
			workerCount++;
			dtos.add(worker.getDto());
			System.out.println(worker.getId()+"\t"+workerMaterials.size());
		}
		return dtos;
	}
	
	private void initWorkers() {
		workerMap=new HashMap<Integer,Runnable>();
		workerCount=0;
		resultHandler=new ResultHandler(resultQueue,runRepo,runMaterialRepo);
		Future<?> f=resultExecutor.submit(resultHandler);
		resultHandler.setMyFuture(f);
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
		System.out.println("Starting worker "+workerId);
		Worker worker=(Worker) workerMap.get(workerId);
		Future<?> future=workerExecutor.submit(workerMap.get(workerId));
		worker.setMyFuture(future);
	}

	@Override
	public List<WorkerDTO> startAll(int runId) {
		if(workerMap!=null) {
			for(Integer workerId : workerMap.keySet()) {
				Worker worker=(Worker) workerMap.get(workerId);
				if(worker.getRunId()==runId 
						&& (worker.getStatus()==WorkerStatus.READY || worker.getStatus()==WorkerStatus.STOPPED)) {
					this.startOne(worker.getId());
				}
			}
		}
		return getActiveWorkers(runId);
	}
	
	@Override
	public void pauseOne(int workerId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pauseAll(int runId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopOne(int workerId) {
		System.out.println("Stopping worker "+workerId);
		Worker worker=(Worker) workerMap.get(workerId);
		worker.getMyFuture().cancel(true);
	}

	@Override
	public List<WorkerDTO> stopAll(int runId) {
		if(workerMap!=null) {
			for(Integer workerId : workerMap.keySet()) {
				Worker worker=(Worker) workerMap.get(workerId);
				if(worker.getRunId()==runId 
						&& worker.getStatus()==WorkerStatus.RUNNING) {
					this.stopOne(worker.getId());
				}
			}
		}
		
		return getActiveWorkers(runId);
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
	
	@Override
	public boolean isExecuting(int runId) {
		boolean executing=false;
		if(workerMap!=null) {
			for(Integer workerId : workerMap.keySet()) {
				CreateMaterialWorker worker=(CreateMaterialWorker) workerMap.get(workerId);
				if(worker.getRunId()==runId && worker.getStatus() == WorkerStatus.RUNNING) {
					executing=true;
					break;
				}
			}
		}
		return executing;
	}

	@Override
	public int getResultQueueSize() {
		return resultQueue.size();
	}

	

}
