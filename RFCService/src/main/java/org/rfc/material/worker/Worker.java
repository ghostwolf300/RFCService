package org.rfc.material.worker;

public abstract class Worker {
	
	private int id;
	private int runId;
	
	public Worker() {
		super();
	}

	public Worker(int id, int runId) {
		super();
		this.id = id;
		this.runId = runId;
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
	
}
