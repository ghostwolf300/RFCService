package org.rfc.material.message;

import java.util.List;

import org.rfc.material.dto.CreateMaterialResultDTO;
import org.rfc.material.dto.RunDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/material/message")
public class MessageRestController {
	
	@GetMapping(value="/list",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getMessages(@RequestParam int runId,@RequestParam int materialStatus){
		List<CreateMaterialResultDTO> results=null;
		return new ResponseEntity<List<CreateMaterialResultDTO>>(results,HttpStatus.OK);
	}
	

}
