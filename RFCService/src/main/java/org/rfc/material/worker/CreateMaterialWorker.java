package org.rfc.material.worker;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import org.rfc.material.Material;
import org.rfc.material.dto.CreateMaterialResultDTO;
import org.rfc.material.dto.WorkerDTO;


public class CreateMaterialWorker extends Worker implements Runnable {
	
	private BlockingQueue<CreateMaterialResultDTO> resultQueue;
	private Queue<Material> materialQueue;
	private int errorCount;
	private int successCount;
	private WorkerDTO dto;
	
	public CreateMaterialWorker() {
		super();
	}
	
	public CreateMaterialWorker(int id,int runId) {
		super(id,runId);
	}
	
	public CreateMaterialWorker(int id, int runId,List<Material> materials,BlockingQueue<CreateMaterialResultDTO> resultQueue) {
		super(id,runId);
		this.materialQueue=new LinkedList<Material>(materials);
		this.resultQueue=resultQueue;
		dto=new WorkerDTO(this.getId(),materialQueue.size(),0,0,this.status);
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public WorkerDTO getDto() {
		return dto;
	}

	public void setDto(WorkerDTO dto) {
		this.dto = dto;
	}

	@Override
	public void run(){
		status=WorkerStatus.RUNNING;
		dto.setCurrentStatus(status);
		Material m;
		while((m=materialQueue.poll())!=null) {
			if(Thread.currentThread().isInterrupted()) {
				System.out.println("Stopping thread...");
				status=WorkerStatus.STOPPED;
				dto.setCurrentStatus(status);
				break;
			}
			System.out.println(this.getId()+"\tCreating material: "+m.getMaterialId());
			CreateMaterialResultDTO result=new CreateMaterialResultDTO(runId);
			result.setWorkerId(String.valueOf(this.getId()));
			result.setMaterial(m.getMaterialId());
			result.setStatus(1);
			resultQueue.add(result);
			dto.addSuccess();
			
			//This is not needed when material creation logic is implemented
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {
				System.out.println("Interrupted while sleeping!");
				status=WorkerStatus.STOPPED;
				dto.setCurrentStatus(status);
				break;
			}
		}
		System.out.println(this.id+" Exiting material loop. Status: "+this.getStatus()+"\tMaterial Queue size: "+materialQueue.size());
		if(status!=WorkerStatus.STOPPED) {
			dto.setCurrentStatus(WorkerStatus.FINISHED);
		}	
	}

}
