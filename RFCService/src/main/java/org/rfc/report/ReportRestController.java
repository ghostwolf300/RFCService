package org.rfc.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportRestController {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/report/test")
	@ResponseBody
	public ResponseEntity<?> test(){
		reportService.test();
		return null;
		
	}
	
}
