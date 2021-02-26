package org.rfc.material;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MaterialRepository extends MongoRepository<Material, String> {
	
	public List<Material> findByRunId(int runId);
	public List<Material> findByRunIdAndMaterialIdIn(int runId,List<String> materialIdList);
	public Material findByRunIdAndMaterialId(int runId,String materialId);
	public Material findByRunIdAndRowNumber(int runId,int rowNumber);
	public int deleteByRunId(int runId);

}
