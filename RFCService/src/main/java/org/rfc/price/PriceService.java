package org.rfc.price;

import org.rfc.exception.RFCException;

public interface PriceService {
	
	public void savePriceCondition(PriceConditionDTO condition) throws RFCException;
	
}
