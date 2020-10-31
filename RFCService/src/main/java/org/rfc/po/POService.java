package org.rfc.po;

import org.rfc.exception.RFCException;

public interface POService {
	
	public ResponseDTO saveOrder(PurchaseOrderDTO order) throws RFCException;
	public PurchaseOrderDTO getDetails(long poId) throws RFCException;
	
}
