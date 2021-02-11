package org.rfc.material.worker;

import java.util.List;
import java.util.concurrent.Callable;

import org.rfc.material.dto.CreateMaterialResultDTO;
import org.rfc.material.dto.MaterialDTO;
import org.rfc.material.dto.WorkerResultDTO;

public class CreateMaterialWorker extends Worker implements Callable<WorkerResultDTO> {
	
	private ResultHandler resultHandler;
	
	public CreateMaterialWorker() {
		super();
	}
	
	public CreateMaterialWorker(int id,int runId) {
		super(id,runId);
	}
	
	public CreateMaterialWorker(int id, int runId,List<MaterialDTO> materials,ResultHandler resultHandler) {
		super(id,runId);
		this.resultHandler=resultHandler;
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
