package org.rfc.material.worker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.material.dto.ResponseDTO;
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
@RequestMapping("/material/workers")
public class WorkerRestController {
	
	private static final Logger logger = LogManager.getLogger(WorkerRestController.class);
	
	@Autowired
	private WorkerService workerService;
	
	@GetMapping(value="/create",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> createWorkers(@RequestParam int runId,@RequestParam int maxMaterials){
		List<WorkerDTO> workers=workerService.createWorkers(runId, maxMaterials);
		return new ResponseEntity<List<WorkerDTO>>(workers,HttpStatus.OK);
	}
	
	@GetMapping(value="/start",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> startWorker(@RequestParam int id){
		workerService.startOne(id);
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(130,"worker "+id+" started"),HttpStatus.OK);
	}
	
	@GetMapping(value="/stop",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> stopWorker(@RequestParam int id){
		workerService.stopOne(id);
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(135,"worker "+id+" stopped"),HttpStatus.OK);
	}
	
}
