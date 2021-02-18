package org.rfc.material.worker;

import java.util.concurrent.Future;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

public abstract class Worker implements Runnable {
	
	protected int id;
	protected int runId;
	protected WorkerStatus status;
	protected boolean execute=false;
	protected boolean stop=false;
	protected Future<?> myFuture;
	protected JCoDestination destination;
	protected long startTime;
	protected long endTime;
	
	public Worker(JCoDestination destination) {
		super();
		this.destination=destination;
		this.status=WorkerStatus.READY;
	}

	public Worker(int id, int runId,JCoDestination destination) {
		super();
		this.id = id;
		this.runId = runId;
		this.destination=destination;
		this.status=WorkerStatus.READY;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public WorkerStatus getStatus() {
		return status;
	}

	public void setStatus(WorkerStatus status) {
		this.status = status;
	}

	public Future<?> getMyFuture() {
		return myFuture;
	}

	public void setMyFuture(Future<?> myFuture) {
		this.myFuture = myFuture;
	}
	
	public JCoDestination getDestination() {
		return destination;
	}

	public void setDestination(JCoDestination destination) {
		this.destination = destination;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}
	
	public void run() {
		startTime=System.currentTimeMillis();
		status=WorkerStatus.RUNNING;
		JCoContext.begin(destination);
		try {
			doWork();
		}
		catch(JCoException ex) {
			System.out.println("Worker: "+id+" SAP connector error!");
			status=WorkerStatus.ERROR;
		} 
		catch (InterruptedException ex) {
			System.out.println("Worker: "+id+" Interrupted!");
			status=WorkerStatus.STOPPED;
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				e.printStackTrace();
			}
		}
		if(status==WorkerStatus.RUNNING) {
			status=WorkerStatus.FINISHED;
		}
		
	}
	
	protected abstract void doWork() throws JCoException,InterruptedException;
	
}
