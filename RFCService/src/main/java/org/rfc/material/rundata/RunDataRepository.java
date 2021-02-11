package org.rfc.material.rundata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RunDataRepository extends JpaRepository<RunData, RunDataKey>,RunDataRepositoryCustom {
	
	public List<RunData> findByIdRunId(int runId);
	
}
