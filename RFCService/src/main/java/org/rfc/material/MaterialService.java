package org.rfc.material;

import java.util.List;
import java.util.Map;

import org.rfc.material.dto.HeaderColumnDTO;
import org.rfc.material.dto.TemplateDTO;
import org.rfc.material.dto.ResponseDTO;

public interface MaterialService {
	public Map<String,List<BAPIField>> getFieldMap();
	public List<TemplateDTO> getTemplates();
	public TemplateDTO getTemplate(int id);
	public ResponseDTO saveTemplate(TemplateDTO template);
	public List<HeaderColumnDTO> getUploadHeaders(int templateId);
	
}
