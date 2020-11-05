package org.rfc.price;

import org.rfc.exception.RFCException;
import org.rfc.sap.SapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoParameterList;

@Service("priceService")
public class PriceServiceImpl implements PriceService {
	
	@Autowired
	private SapService sapService;
	
	@Override
	public void savePriceCondition(PriceConditionDTO condition) throws RFCException {
		
		JCoFunctionTemplate functionTemplate=null;
		JCoFunction fPriceConditions=null;
		JCoFunction fCommit=null;
		JCoParameterList imports=null;
		JCoParameterList exports=null;
		JCoParameterList tables=null;
		
		JCoDestination destination=sapService.getDestination();
		
		try {
			functionTemplate=destination.getRepository().getFunctionTemplate("BAPI_PRICES_CONDITIONS");
			fPriceConditions=functionTemplate.getFunction();
		}
		catch(JCoException e) {
			e.printStackTrace();
			throw new RFCException(120,"Function BAPI_PRICES_CONDITIONS not found!",e);
		}
		try {
			functionTemplate=destination.getRepository().getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
			fCommit=functionTemplate.getFunction();
		}
		catch(JCoException e) {
			throw new RFCException(120,"Function BAPI_TRANSACTION_COMMIT not found!",e);
		}
		
		imports=fPriceConditions.getImportParameterList();
		exports=fPriceConditions.getExportParameterList();
		tables=fPriceConditions.getTableParameterList();
		
		try {
			JCoContext.begin(destination);
			fPriceConditions.execute(destination);
			fCommit.execute(destination);
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

	}

}
