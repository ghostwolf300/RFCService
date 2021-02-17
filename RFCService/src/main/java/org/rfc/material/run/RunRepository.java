package org.rfc.material.run;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RunRepository extends JpaRepository<Run, Integer> {
	
	public List<Run> findAllByTemplateId(int templateId);
	@Modifying
	@Query("update Run r set r.materialCount=:materialCount where r.id=:id")
	@Transactional
	public void updateMaterialCount(@Param(value="id") int id, @Param(value="materialCount") int materialCount);
	
	@Modifying
	@Query("update Run r set r.errorCount=r.errorCount+:addCount where r.id=:id")
	@Transactional
	public void addToErrorCount(@Param(value="id") int id,@Param(value="addCount") int addCount);
	
	@Modifying
	@Query("update Run r set r.successCount=r.successCount+:addCount where r.id=:id")
	@Transactional
	public void addToSuccessCount(@Param(value="id") int id,@Param(value="addCount") int addCount);
	
	@Modifying
	@Query("update Run r set r.successCount=0, r.errorCount=0 where r.id=:id")
	@Transactional
	public void resetCounters(@Param(value="id") int id);
	
	
}
