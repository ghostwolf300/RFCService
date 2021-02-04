package org.rfc.material.rundata;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.rfc.material.dto.RunMaterialDTO;

public class RunDataRepositoryImpl implements RunDataRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<RunMaterialDTO> getRunMaterials(int runId) {
		Query qry=em.createNamedQuery("RunMaterials");
		qry.setParameter("runId", runId);
		@SuppressWarnings("unchecked")
		List<RunMaterialDTO> runMaterials=qry.getResultList();
		return runMaterials;
	}

}
