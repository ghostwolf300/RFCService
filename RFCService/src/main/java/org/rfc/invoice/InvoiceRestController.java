package org.rfc.invoice;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceRestController {
	
	private static final Logger logger = LogManager.getLogger(InvoiceRestController.class);
	
	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping(value="/invoice/findOpenInvoices",produces="application/json")
	@ResponseBody
	public ResponseEntity<?> findOpenInvoices(){
		//TODO : retrieve invoices from SAP
		List<InvoiceDTO> invoices=invoiceService.findOpenInvoices();
		return new ResponseEntity<List<InvoiceDTO>>(invoices,HttpStatus.OK);
	}
	
	
}
