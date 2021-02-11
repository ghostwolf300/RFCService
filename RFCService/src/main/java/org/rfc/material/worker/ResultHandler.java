package org.rfc.material.worker;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.rfc.material.dto.CreateMaterialResultDTO;

public class ResultHandler implements Callable<Integer> {
	
	private boolean polling;
	private int saveCount=0;
	private Queue<CreateMaterialResultDTO> resultQueue;
	
	public ResultHandler() {
		resultQueue=new ConcurrentLinkedQueue<CreateMaterialResultDTO>();
		this.polling=true;
	}
	
	public boolean isPolling() {
		return polling;
	}

	public void setPolling(boolean polling) {
		this.polling = polling;
	}

	@Override
	public Integer call() throws Exception {
		
		while(polling) {
			CreateMaterialResultDTO result=resultQueue.poll();
			if(result!=null) {
				System.out.println("Saving results... ID#:"+result.getWorkerId()+"\tMATERIAL: "+result.getMaterial()+"\tSTATUS: "+result.getStatus());
				saveCount++;
			}
		}
		return saveCount;
	}
	
	public synchronized void handle(CreateMaterialResultDTO result) {
		resultQueue.add(result);
	}

}
