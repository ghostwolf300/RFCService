package org.rfc.material.run;

import java.util.List;

import org.rfc.material.dto.RunDTO;
import org.rfc.material.dto.WorkerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/material/run")
public class RunRestController {
	
	@Autowired
	private RunService runService;
	
	@GetMapping(value="/status",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> queryStatus(@RequestParam int runId){
		RunDTO run=runService.getRun(runId);
		return new ResponseEntity<RunDTO>(run,HttpStatus.OK);
	}
	
	@GetMapping(value="/reset",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> resetRun(@RequestParam int runId){
		RunDTO run=runService.resetRun(runId);
		return new ResponseEntity<RunDTO>(run,HttpStatus.OK);
	}
	
}
