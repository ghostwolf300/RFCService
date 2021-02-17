package org.rfc.material.run;

import org.rfc.material.dto.RunDTO;

public interface RunService {
	public RunDTO getRun(int runId);
	public RunDTO resetRun(int runId);
}
