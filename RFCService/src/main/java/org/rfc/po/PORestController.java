package org.rfc.po;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PORestController {
	
	@Autowired
	private POService poService;
	
	@PostMapping(value="/po/save",consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> saveOrder(@RequestBody PurchaseOrderDTO order){
		ResponseDTO response=poService.saveOrder(order);
		
		//for(ResponseLineDTO line : response.getLines()) {
		//	System.out.println(line.getType()+"\t"+line.getNumber()+"\t"+line.getId()+"\t"+line.getRow()+"\t"+line.getMessage());
		//}
		
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
	}
	
	
	public ResponseEntity<?> getConfirmations(){
		return null;
	}
	
	
	
	
}
