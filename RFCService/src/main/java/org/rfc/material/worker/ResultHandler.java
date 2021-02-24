package org.rfc.material.worker;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.material.dto.CreateMaterialResultDTO;
import org.rfc.material.dto.ReturnMessageDTO;
import org.rfc.material.messages.ReturnMessage;
import org.rfc.material.messages.ReturnMessageRepository;
import org.rfc.material.run.RunRepository;
import org.rfc.material.runmaterial.RunMaterial;
import org.rfc.material.runmaterial.RunMaterialKey;
import org.rfc.material.runmaterial.RunMaterialRepository;

public class ResultHandler implements Runnable {
	
	private static final Logger logger=LogManager.getLogger(ResultHandler.class);
	
	private boolean polling;
	private int saveCount=0;
	private BlockingQueue<CreateMaterialResultDTO> resultQueue;
	private Future<?> myFuture;
	
	private RunRepository runRepo;
	private RunMaterialRepository runMaterialRepo;
	private ReturnMessageRepository messageRepo;
	
	public ResultHandler(BlockingQueue<CreateMaterialResultDTO> resultQueue,RunRepository runRepo,RunMaterialRepository runMaterialRepo,ReturnMessageRepository messageRepo) {
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
			//System.out.println("Queue size: "+resultQueue.size());
			CreateMaterialResultDTO result=null;
			
			while((result=resultQueue.poll())!=null) {
				//System.out.println("Saving results... ID#:"+result.getWorkerId()+"\tMATERIAL: "+result.getMaterial()+"\tSTATUS: "+result.getStatus());
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
				RunMaterial rm=runMaterialRepo.getOne(new RunMaterialKey(result.getRunId(),result.getMaterial()));
				for(ReturnMessageDTO msg : result.getMessages()) {
					ReturnMessage m=new ReturnMessage(msg,rm);
					messageRepo.save(m);
				}
				runMaterialRepo.flush();
				runRepo.flush();
				messageRepo.flush();
			}
			//System.out.println("No results... waiting data");
			try {
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) {
				System.out.println("ResultHandler interrupted while sleeping!");
				polling=false;
			}
			
		}
		logger.log(Level.INFO, "Result handler shutting down.");
	}
	
	public int getQueueSize() {
		return resultQueue.size();
	}

}
