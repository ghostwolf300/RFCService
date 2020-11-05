package org.rfc.confbuilder;

import org.rfc.orderconf.SupplierConfirmationDTO;

public abstract class ConfirmationBuilder {
	
	protected ConfirmationBuilder() {
		super();
	}
	
	public abstract SupplierConfirmationDTO build(String text);
	
}
