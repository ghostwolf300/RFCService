package org.rfc.material;

import java.util.List;

import org.rfc.material.dto.HeaderColumnDTO;

public interface TemplateRepositoryCustom {

	public List<HeaderColumnDTO> getUploadHeader(int templateId);
	
}
