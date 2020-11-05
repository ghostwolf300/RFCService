package org.rfc.confbuilder;

import org.rfc.exception.RFCException;

public class ConfirmationBuilderFactory {
	
	public ConfirmationBuilderFactory() {
		super();
	}
	
	public ConfirmationBuilder getBuilder(String supplier) throws RFCException {
		if(supplier.equals("test")) {
			return new ParkerHannifinConfirmationBuilder();
		}
		else {
			throw new RFCException(201,"No confirmation builder for supplier "+supplier);
		}
	}
	
}
