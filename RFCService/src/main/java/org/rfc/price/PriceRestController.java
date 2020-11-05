package org.rfc.price;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.po.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceRestController {
	
	private static final Logger logger = LogManager.getLogger(PriceRestController.class);
	
	@Autowired
	private PriceService priceService;
	
	@GetMapping(value="/material/salesPrice/test",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> test(){
		priceService.savePriceCondition(null);
		return new ResponseEntity<String>("This was a test",HttpStatus.OK);
	}

}
