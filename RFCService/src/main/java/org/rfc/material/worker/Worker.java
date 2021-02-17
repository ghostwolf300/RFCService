package org.rfc.material.worker;

import java.util.concurrent.Future;

public abstract class Worker {
	
	protected int id;
	protected int runId;
	protected WorkerStatus status;
	protected boolean execute=false;
	protected boolean stop=false;
	protected Future<?> myFuture;
	
	public Worker() {
		super();
		this.status=WorkerStatus.READY;
	}

	public Worker(int id, int runId) {
		super();
		this.id = id;
		this.runId = runId;
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
	
}
