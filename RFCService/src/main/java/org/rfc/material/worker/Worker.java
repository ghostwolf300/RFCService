package org.rfc.material.worker;

import java.util.concurrent.Future;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;

public abstract class Worker implements Runnable {
	
	private static final Logger logger=LogManager.getLogger(Worker.class);
	
	protected int id;
	protected int runId;
	protected WorkerStatus status;
	protected boolean execute=false;
	protected boolean stop=false;
	protected Future<?> myFuture;
	protected JCoDestination destination;
	protected long startTime;
	protected long endTime;
	
	protected JCoFunction function;
	
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
		logger.log(Level.INFO, "runId: "+runId+" workerId: "+id+"\tStarting run...");
		startTime=System.currentTimeMillis();
		status=WorkerStatus.RUNNING;
		try {
			doWork();
		}
		catch(JCoException ex) {
			logger.log(Level.ERROR,"runId:"+runId+", workerId:"+id+"\tSAP Connector error! "+ex.getMessage());
			status=WorkerStatus.ERROR;
		} 
		catch (InterruptedException ex) {
			logger.log(Level.INFO, "runId: "+runId+" workerId: "+id+"\tWorker interrupted.");
			status=WorkerStatus.STOPPED;
		}
		finally {
			logger.log(Level.INFO,"runId:"+runId+", workerId:"+id+"\tRun finished. Closing connection...");
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				logger.log(Level.ERROR,"runId:"+runId+", workerId:"+id+"\tError while closing connection! "+e.getMessage());
				//e.printStackTrace();
			}
			if(status==WorkerStatus.RUNNING) {
				status=WorkerStatus.FINISHED;
			}
			endTime=System.currentTimeMillis();
			logger.log(Level.INFO,"runId:"+runId+", workerId:"+id+"\tRun finished. Status="+status);
		}
		
		
	}
	
	protected abstract void doWork() throws JCoException,InterruptedException;
	
	protected void initialize(String functionName) throws JCoException {
		JCoRepository repo=destination.getRepository();
		JCoFunctionTemplate template=repo.getFunctionTemplate(functionName);
		function=template.getFunction();
	
	}
	
	protected void executeFunction() throws JCoException {
		JCoContext.begin(destination);
		function.execute(destination);
		JCoContext.end(destination);
	}
	
	protected long getCurrentRunTime() {
		return System.currentTimeMillis()-startTime;
	}
	
	protected long getTotalRunTime() {
		return endTime-startTime;
	}
	
}
