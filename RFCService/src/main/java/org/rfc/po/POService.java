package org.rfc.po;

public interface POService {
	
	public ResponseDTO saveOrder(PurchaseOrderDTO order);
	public PurchaseOrderDTO getDetails(long poId);
	
}
