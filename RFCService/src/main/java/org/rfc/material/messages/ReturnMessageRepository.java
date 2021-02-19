package org.rfc.material.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ReturnMessageRepository extends JpaRepository<ReturnMessage, Integer> {
	
	@Modifying
	@Transactional
	public int deleteByRunMaterialIdRunId(int runId);
	
}
