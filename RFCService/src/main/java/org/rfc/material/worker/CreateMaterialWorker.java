package org.rfc.material.worker;

import java.util.List;
import java.util.concurrent.Callable;

import org.rfc.material.Material;
import org.rfc.material.dto.CreateMaterialResultDTO;
import org.rfc.material.dto.WorkerDTO;
import org.rfc.material.dto.WorkerResultDTO;

public class CreateMaterialWorker extends Worker implements Callable<WorkerResultDTO> {
	
	private ResultHandler resultHandler;
	private List<Material> materials;
	private int statusCode;
	private int errorCount;
	private int successCount;
	private WorkerDTO dto;
	
	public CreateMaterialWorker() {
		super();
	}
	
	public CreateMaterialWorker(int id,int runId) {
		super(id,runId);
	}
	
	public CreateMaterialWorker(int id, int runId,List<Material> materials,ResultHandler resultHandler) {
		super(id,runId);
		this.materials=materials;
		this.resultHandler=resultHandler;
		statusCode=0;
		dto=new WorkerDTO(this.getId(),materials.size(),0,0,statusCode);
	}
	
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
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
	public WorkerResultDTO call() throws Exception {
		for(int i=0;i<10;i++) {
			System.out.println("#:"+this.getId()+" Executing loop: "+i);
			CreateMaterialResultDTO result=new CreateMaterialResultDTO();
			result.setWorkerId(String.valueOf(this.getId()));
			result.setMaterial("TEST123");
			result.setStatus(1);
			resultHandler.handle(result);
			Thread.sleep(2000);
		}
		WorkerResultDTO wr=new WorkerResultDTO();
		return wr;
	}

}
