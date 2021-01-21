package org.rfc.material;

import java.util.List;
import java.util.Map;

import org.rfc.material.dto.MaterialTemplateDTO;
import org.rfc.material.dto.ResponseDTO;

public interface MaterialService {
	public Map<String,List<BAPIField>> getFieldMap();
	public ResponseDTO saveMaterialTemplate(MaterialTemplateDTO template);
	
}
