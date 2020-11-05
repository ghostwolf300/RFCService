package org.rfc.orderconf;

import java.util.List;

import org.rfc.exception.RFCException;
import org.springframework.web.multipart.MultipartFile;

public interface OrderConfirmationService {
	
	public void test() throws RFCException;
	public SupplierConfirmationDTO readConfirmationPdf(MultipartFile mf,String supplier) throws RFCException;
	
	
	
}
