package org.rfc.material;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MaterialRepository extends MongoRepository<Material, String> {
	
	public List<Material> findByRunId(int runId);
	public Material findByMaterialId(String materialId);
	public Material findByRowNumber(int rowNumber);
	public int deleteByRunId(int runId);
	
}
