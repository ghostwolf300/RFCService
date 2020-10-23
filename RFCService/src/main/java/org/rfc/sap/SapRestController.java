package org.rfc.sap;

import org.rfc.po.PurchaseOrderDTO;
import org.rfc.po.ResponseDTO;
import org.rfc.po.ResponseLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SapRestController {
	
	@Autowired
	private SapService sapService;
	
	@PostMapping(value="/sap/login",consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody SapUserDTO user){
		boolean ok=sapService.loginUser(user);
		if(ok) {
			return new ResponseEntity<SapUserDTO>(user,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Login failed!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="/sap/logout")
	@ResponseBody
	public ResponseEntity<?> logout(){
		sapService.logoutUser();
		return new ResponseEntity<String>("done",HttpStatus.OK);
		
	}

}
