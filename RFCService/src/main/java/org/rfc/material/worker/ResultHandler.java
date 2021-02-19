package org.rfc.material.worker;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.rfc.material.dto.CreateMaterialResultDTO;
import org.rfc.material.dto.ReturnMessageDTO;
import org.rfc.material.message.MessageRepository;
import org.rfc.material.run.RunRepository;
import org.rfc.material.runmaterial.RunMaterialRepository;

public class ResultHandler implements Runnable {
	
	private boolean polling;
	private int saveCount=0;
	private BlockingQueue<CreateMaterialResultDTO> resultQueue;
	private Future<?> myFuture;
	
	private RunRepository runRepo;
	private RunMaterialRepository runMaterialRepo;
	private MessageRepository messageRepo;
	
	public ResultHandler(BlockingQueue<CreateMaterialResultDTO> resultQueue,RunRepository runRepo,RunMaterialRepository runMaterialRepo,MessageRepository messageRepo) {
		this.resultQueue=resultQueue;
		this.runRepo=runRepo;
		this.runMaterialRepo=runMaterialRepo;
		this.messageRepo=messageRepo;
		this.polling=true;
	}
	
	public boolean isPolling() {
		return polling;
	}

	public void setPolling(boolean polling) {
		this.polling = polling;
	}

	public Future<?> getMyFuture() {
		return myFuture;
	}

	public void setMyFuture(Future<?> myFuture) {
		this.myFuture = myFuture;
	}

	@Override
	public void run() {
		
		while(polling) {
			if(Thread.currentThread().isInterrupted()) {
				polling=false;
				break;
			}
			System.out.println("Queue size: "+resultQueue.size());
			CreateMaterialResultDTO result=null;
			
			while((result=resultQueue.poll())!=null) {
				System.out.println("Saving results... ID#:"+result.getWorkerId()+"\tMATERIAL: "+result.getMaterial()+"\tSTATUS: "+result.getStatus());
				runMaterialRepo.updateStatus(result.getRunId(), result.getMaterial(), result.getStatus());
				
				switch(result.getStatus()) {
					case(1) : 
						runRepo.addToSuccessCount(result.getRunId(), 1);
						break;
					case(2) :
						//add to warning count
						break;
					case(3) :
						runRepo.addToErrorCount(result.getRunId(), 1);
						break;
				}
				
				for(ReturnMessageDTO msg : result.getMessages()) {
					//save messages
				}
				runMaterialRepo.flush();
				runRepo.flush();
				messageRepo.flush();
				
				//simulate db operation time
//				try {
//					Thread.sleep(1500);
//				} 
//				catch (InterruptedException e) {
//					System.out.println("ResultHandler interrupted while sleeping!");
//					polling=false;
//				}
			}
			System.out.println("No results... waiting data");
			try {
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) {
				System.out.println("ResultHandler interrupted while sleeping!");
				polling=false;
			}
			
		}
		System.out.println("Result handler shutting down...");
	}
	
	public int getQueueSize() {
		return resultQueue.size();
	}

}
