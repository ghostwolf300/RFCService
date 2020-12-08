package org.rfc.invoice;

import java.util.List;

public interface InvoiceService {
	
	public List<InvoiceDTO> findOpenInvoices();
	
}
