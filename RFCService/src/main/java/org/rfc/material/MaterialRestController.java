package org.rfc.material;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.material.dto.HeaderColumnDTO;
import org.rfc.material.dto.ResponseDTO;
import org.rfc.material.dto.RunDTO;
import org.rfc.material.dto.RunDataWrapperDTO;
import org.rfc.material.dto.TemplateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaterialRestController {
	
	private static final Logger logger = LogManager.getLogger(MaterialRestController.class);
	
	@Autowired
	private MaterialService materialService;
	
	@GetMapping(value="/material/getMaterialTemplate",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getMaterialTemplate(@RequestParam("templateId") int id){
		TemplateDTO template=materialService.getTemplate(id);
		if(template!=null) {
			return new ResponseEntity<TemplateDTO>(template,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Not found in DB",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="/material/saveMaterialTemplate",consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> saveMaterialTemplate(@RequestBody TemplateDTO template){
		System.out.println("id: "+template.getId());
		System.out.println("name: "+template.getName());
		
		materialService.saveTemplate(template);
		
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(111,"template received"),HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value="/material/getUploadHeaders",produces="application/json")
	public ResponseEntity<?> getUploadHeaders(@RequestParam("templateId") int id){
		List<HeaderColumnDTO> headerColumns=materialService.getUploadHeaders(id);
		return new ResponseEntity<List<HeaderColumnDTO>>(headerColumns,HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value="/material/runs",produces="application/json")
	public ResponseEntity<?> getRuns(@RequestParam("templateId") int templateId){
		List<RunDTO> runs=materialService.getRuns(templateId);
		return new ResponseEntity<List<RunDTO>>(runs,HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value="/material/saveRun",consumes="application/json",produces="application/json")
	public ResponseEntity<?> saveRun(@RequestBody RunDTO run){
		System.out.println(run.getId()+"\t"+run.getName()+"\t"+run.getTemplateId());
		run=materialService.saveRun(run);
		return new ResponseEntity<RunDTO>(run,HttpStatus.OK);
	}
	
	@PostMapping(value="/material/saveRunData",consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> saveRunData(@RequestBody RunDataWrapperDTO runData){
		System.out.println("Template ID: "+runData.getTemplateId());
		System.out.println("Materials#: "+runData.getRunDataList().size());
		ResponseDTO response=materialService.saveRunData(runData);
		if(response.getStatusCode()==999) {
			return new ResponseEntity<ResponseDTO>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
	}
	
	@GetMapping(value="/material/deleteRun",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> deleteRun(@RequestParam int runId){
		ResponseDTO response=materialService.deleteRun(runId);
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
	}
	
	
	public ResponseEntity<?> execute(@RequestParam boolean run){
		return null;
	}
	
	@GetMapping(value="/material/runStatus",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> queryRunStatus(){
		return null;
	}
	
}
