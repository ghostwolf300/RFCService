package org.rfc.report;

import java.util.ArrayList;
import java.util.List;

import org.rfc.exception.RFCException;
import org.rfc.sap.SapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private SapService sapService;
	
	@Override
	public void test() {
		JCoFunctionTemplate functionTemplate=null;
		JCoFunction fReadTable=null;
		JCoParameterList imports=null;
		JCoParameterList exports=null;
		JCoParameterList tables=null;
		
		JCoDestination destination=sapService.getDestination();
		
		try {
			functionTemplate=destination.getRepository().getFunctionTemplate("RFC_READ_TABLE");
			fReadTable=functionTemplate.getFunction();
		}
		catch(JCoException e) {
			e.printStackTrace();
			throw new RFCException(120,"Function RFC_READ_TABLE not found!",e);
		}
		
		imports=fReadTable.getImportParameterList();
		exports=fReadTable.getExportParameterList();
		tables=fReadTable.getTableParameterList();
		
		JCoTable tOptions=tables.getTable("OPTIONS");
		JCoTable tFields=tables.getTable("FIELDS");
		JCoTable tData=tables.getTable("DATA");
		
		imports.setValue("QUERY_TABLE","ANLC");
		imports.setValue("DELIMITER", ";");
		imports.setValue("ROWCOUNT",100);
		
		tOptions.appendRow();
		tOptions.setValue("TEXT", "BUKRS = '13'");
		
		List<String> fieldNames=new ArrayList<String>();
		fieldNames.add("BUKRS");
		fieldNames.add("ANLN1");
		fieldNames.add("ANLN2");
		fieldNames.add("GJAHR");
		
		for(String fieldName : fieldNames) {
			tFields.appendRow();
			tFields.setValue("FIELDNAME", fieldName);
		}
		
		
		try {
			JCoContext.begin(destination);
			fReadTable.execute(destination);
		}
		catch(JCoException e) {
			e.printStackTrace();
			throw new RFCException(130,"RFC call failed!",e);
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				throw new RFCException(140,"Encountered error when closing connection.",e);
			}
		}
		
		System.out.println("Data size: "+tData.getNumRows());
		
		JCoFieldIterator iter=tData.getFieldIterator();
		while(iter.hasNextField()) {
			JCoField fld=iter.nextField();
			System.out.println(fld.getName());
		}
		
		if(!tData.isEmpty()) {
			do {
				
				System.out.println(tData.getString("WA"));
			}
			while(tData.nextRow());
		}

	}

}
