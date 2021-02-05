package org.rfc.material;

import java.util.List;
import java.util.Map;

import org.rfc.material.dto.HeaderColumnDTO;
import org.rfc.material.dto.TemplateDTO;
import org.rfc.material.dto.ResponseDTO;
import org.rfc.material.dto.RunDTO;
import org.rfc.material.dto.RunDataDTO;

public interface MaterialService {
	public Map<String,List<BAPIField>> getFieldMap();
	public List<TemplateDTO> getTemplates();
	public TemplateDTO getTemplate(int id);
	public ResponseDTO saveTemplate(TemplateDTO template);
	public List<HeaderColumnDTO> getUploadHeaders(int templateId);
	public List<RunDTO> getRuns(int templateId);
	public RunDTO saveRun(RunDTO run);
	public ResponseDTO saveRunData(List<RunDataDTO> runDataList);
	public ResponseDTO deleteRun(int runId);
	public RunDTO getRun(int runId);
	
}
