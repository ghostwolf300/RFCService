package org.rfc.orderconf;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OrderConfirmationRestController {
	
	@Autowired
	private OrderConfirmationService confirmationService;
	
	@PostMapping(value="/po/confirmations/read", headers="{Content-Type=multipart/form-data,Accept=multipart/form-data}",consumes="multipart/form-data")
	public ResponseEntity<?> read(
			@RequestParam(value="userName",required=false) String userName,
			@RequestParam(value="fileType",required=false) String fileType,
			@RequestParam(value="confFile",required=false) MultipartFile file){
		System.out.println("We got something...");
		return new ResponseEntity<String>("This was a test",HttpStatus.OK);
	}
	
	@PostMapping(value="/po/confirmations/uploadFile")
	public ResponseEntity<?> uploadFile(@RequestParam(value="file") MultipartFile file,@RequestParam(value="type") String type,HttpServletRequest request) {
		System.out.println("We got something...");
		SupplierConfirmationDTO confirmation=confirmationService.readConfirmationPdf(file,"test");
		return new ResponseEntity<SupplierConfirmationDTO>(confirmation,HttpStatus.OK);
	   
	}
	
	
	
}
