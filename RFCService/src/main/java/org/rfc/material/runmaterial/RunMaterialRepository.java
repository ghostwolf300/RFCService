package org.rfc.material.runmaterial;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RunMaterialRepository extends JpaRepository<RunMaterial, RunMaterialKey> {
	
	@Modifying
	@Query("update RunMaterial rm set rm.status=:status where rm.id.runId=:runId and rm.id.material=:material")
	@Transactional
	public void updateStatus(@Param(value="runId") int runId,@Param(value="material") String material,@Param(value="status") int status);
	
	public List<RunMaterial> findByIdRunIdAndStatus(int runId,int status);
}
