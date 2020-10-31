package org.rfc.po;


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
	
	@Autowired
	private POService poService;
	
	@PostMapping(value="/po/save",consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> saveOrder(@RequestBody PurchaseOrderDTO order){
		ResponseDTO response=null;
		try {
			response = poService.saveOrder(order);
			return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
		} 
		catch (RFCException e) {
			ExceptionDTO ex=new ExceptionDTO(e);
			return new ResponseEntity<ExceptionDTO>(ex,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/po/find",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getConfirmations(@RequestParam long poId){
		PurchaseOrderDTO order=null;
		try {
			order = poService.getDetails(poId);
			return new ResponseEntity<PurchaseOrderDTO>(order,HttpStatus.OK);
		} 
		catch (RFCException e) {
			ExceptionDTO ex=new ExceptionDTO(e);
			return new ResponseEntity<ExceptionDTO>(ex,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	
}
