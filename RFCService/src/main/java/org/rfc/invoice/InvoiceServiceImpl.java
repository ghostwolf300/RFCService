package org.rfc.invoice;

import java.util.List;

import org.rfc.exception.RFCException;
import org.rfc.sap.SapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private SapService sapService;
	
	@Override
	public List<InvoiceDTO> findOpenInvoices() {
		List<InvoiceDTO> invoices=null;
		String fm="SAP_WAPI_CREATE_WORKLIST";
		
		JCoFunctionTemplate functionTemplate=null;
		JCoFunction fCreateWorklist=null;
		JCoParameterList imports=null;
		JCoParameterList exports=null;
		JCoParameterList tables=null;
		
		JCoDestination destination=sapService.getDestination();
		try {
			functionTemplate=destination.getRepository().getFunctionTemplate(fm);		
		}
		catch(JCoException e) {
			e.printStackTrace();
			throw new RFCException(120,"Function "+fm+" not found!",e);
		}
		
		fCreateWorklist=functionTemplate.getFunction();
		imports=fCreateWorklist.getImportParameterList();
		exports=fCreateWorklist.getExportParameterList();
		tables=fCreateWorklist.getTableParameterList();
		
		exports.setValue("USER", "WIHSUSIVI");
		exports.setValue("LANGUAGE", "EN");
		
		JCoTable tWorklist=tables.getTable("WORKLIST");
		JCoTable tWorklistAttr=tables.getTable("WORKLIST_ATTRIBUTES");
		JCoTable tMessageLines=tables.getTable("MESSAGE_LINES");
		JCoTable tMessageStruct=tables.getTable("MESSAGE_STRUCT");
		
		
		return invoices;
	}

}
