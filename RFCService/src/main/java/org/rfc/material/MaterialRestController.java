package org.rfc.material;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.material.dto.MaterialDescriptionDTO;
import org.rfc.material.dto.MaterialTemplateDTO;
import org.rfc.material.dto.ResponseDTO;
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
	
	@GetMapping(value="/material/getMaterialTemplate",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getMaterialTemplate(@RequestParam("templateId") int id){
		return null;
	}
	
	@PostMapping(value="/material/saveMaterialTemplate",consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> saveMaterialTemplate(@RequestBody MaterialTemplateDTO template){
		System.out.println("id: "+template.getId());
		System.out.println("name: "+template.getName());
		for(MaterialDescriptionDTO md : template.getMaterialDescriptionList()) {
			System.out.println(md.getLangu().getValue()+"\t"+md.getMatlDesc().getValue());
		}
		
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(111,"template received"),HttpStatus.OK);
	}
	
	
	@PostMapping(value="/material/uploadData",consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> uploadData(String[][] data){
		return null;
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
