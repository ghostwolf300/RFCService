package org.rfc.po;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.config.SAPUserBean;
import org.rfc.exception.ExceptionDTO;
import org.rfc.exception.RFCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PORestController {
	
	private static final Logger logger = LogManager.getLogger(PORestController.class);
	
	@Autowired
	private POService poService;
	
	@Autowired
	private SAPUserBean sapUser;
	
	@PostMapping(value="/po/save",consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> saveOrder(@RequestBody PurchaseOrderDTO order){
		ResponseDTO response=null;
		response = poService.saveOrder(order);
		if(response.isTest()) {
			logger.info(sapUser.getUserString()+" created purchase order in test mode");
		}
		else {
			logger.info(sapUser.getUserString()+" created purchase order: "+response.getPoNumber());
		}
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
	}
	
	@GetMapping(value="/po/find",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> findOrder(@RequestParam long poId){
		//System.out.println("Find PO: "+poId);
		PurchaseOrderDTO order=null;
		order = poService.getDetails(poId);
		System.out.println(order.getId());
		return new ResponseEntity<PurchaseOrderDTO>(order,HttpStatus.OK);
	}
	
	
	
	
}
