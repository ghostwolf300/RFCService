package org.rfc.material;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaterialRestController {
	
	private static final Logger logger = LogManager.getLogger(MaterialRestController.class);
	
	@GetMapping(value="/material/downloadTemplate",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getUploadTemplate(){
		return null;
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
