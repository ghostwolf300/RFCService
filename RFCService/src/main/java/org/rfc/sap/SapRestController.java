package org.rfc.sap;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.config.SAPUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SapRestController {
	
	private static final Logger logger = LogManager.getLogger(SapRestController.class);
	
	@Autowired
	private SapService sapService;
	
	@Autowired
	private SAPUserBean sapUser;
	
	@PostMapping(value="/sap/login",consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody SapUserDTO user){
		
		boolean ok=sapService.loginUser(user);
		sapUser.setUserName(user.getUserName());
		sapUser.setClient(user.getClient());
		sapUser.setPassword(user.getPassword());
		logger.info("SAP destination set: "+sapUser.getUserString());
		return new ResponseEntity<SapUserDTO>(user,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/sap/logout")
	@ResponseBody
	public ResponseEntity<?> logout(){
		sapService.logoutUser();
		return new ResponseEntity<String>("done",HttpStatus.OK);
		
	}

}
