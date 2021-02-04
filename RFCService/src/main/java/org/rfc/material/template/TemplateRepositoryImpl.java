package org.rfc.material.template;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.rfc.material.dto.HeaderColumnDTO;

public class TemplateRepositoryImpl implements TemplateRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<HeaderColumnDTO> getUploadHeader(int templateId) {
		Query qry=em.createNamedQuery("UploadHeaders");
		qry.setParameter("templateId", templateId);
		@SuppressWarnings("unchecked")
		List<HeaderColumnDTO> headers=qry.getResultList();
		return headers;
	}

}
