package org.rfc.material.worker;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import org.rfc.material.Material;
import org.rfc.material.dto.CreateMaterialResultDTO;
import org.rfc.material.dto.WorkerDTO;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;


public class CreateMaterialWorker extends Worker {
	
	private BlockingQueue<CreateMaterialResultDTO> resultQueue;
	private Queue<Material> materialQueue;
	private int errorCount;
	private int successCount;
	private WorkerDTO dto;
	
	public CreateMaterialWorker(JCoDestination destination) {
		super(destination);
	}
	
	public CreateMaterialWorker(int id,int runId,JCoDestination destination) {
		super(id,runId,destination);
	}
	
	public CreateMaterialWorker(int id, int runId,List<Material> materials,BlockingQueue<CreateMaterialResultDTO> resultQueue,JCoDestination destination) {
		super(id,runId,destination);
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
		dto.setCurrentStatus(status);
		return dto;
	}

	public void setDto(WorkerDTO dto) {
		this.dto = dto;
	}

	@Override
	protected void doWork() throws JCoException,InterruptedException {
		Material m;
		while((m=materialQueue.poll())!=null) {
			if(Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			System.out.println(this.getId()+"\tCreating material: "+m.getMaterialId());
			CreateMaterialResultDTO result=new CreateMaterialResultDTO(runId);
			result.setWorkerId(String.valueOf(this.getId()));
			result.setMaterial(m.getMaterialId());
			result.setStatus(1);
			resultQueue.add(result);
			dto.addSuccess();
			
			//This is not needed when material creation logic is implemented
			Thread.sleep(1000);
			
		}
		System.out.println(this.id+" Exiting material loop. Status: "+this.getStatus()+"\tMaterial Queue size: "+materialQueue.size());
	}

}
