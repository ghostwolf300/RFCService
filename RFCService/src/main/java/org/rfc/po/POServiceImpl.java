package org.rfc.po;

import java.util.ArrayList;
import java.util.List;

import org.rfc.sap.SapService;
import org.rfc.sap.SapSystem;
import org.rfc.sap.SapSystemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

@Service("poService")
public class POServiceImpl implements POService {
	
	@Autowired
	private SapService sapService;
	
	public final SapSystemFactory factory=new SapSystemFactory();
	
	public ResponseDTO saveOrder(PurchaseOrderDTO order) {
		
		//try to save order
		ResponseDTO response=null;
		SapSystem sap=null;
		try {
			//sap=factory.getSapSystem("TETCLNT280");
			//if this is a new order...
			response=createOrder(order,sap);
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	private ResponseDTO createOrder(PurchaseOrderDTO order,SapSystem sap) throws JCoException {
		JCoDestination destination=sapService.getDestination();
		ResponseDTO response=new ResponseDTO();
		response.setMetaData(order.getMetaData());
		response.setTest(order.isTest());
		
		JCoFunctionTemplate functionTemplate=null;
		
		functionTemplate=destination.getRepository().getFunctionTemplate("BAPI_PO_CREATE1");
		JCoFunction fCreatePO=functionTemplate.getFunction();
		functionTemplate=destination.getRepository().getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
		JCoFunction fCommit=functionTemplate.getFunction();
		
		JCoParameterList imports=fCreatePO.getImportParameterList();
		JCoParameterList exports=fCreatePO.getExportParameterList();
		JCoParameterList tables=fCreatePO.getTableParameterList();
		
		JCoStructure poHeader=imports.getStructure("POHEADER");
		JCoStructure poHeaderX=imports.getStructure("POHEADERX");
		
		JCoTable poItem=tables.getTable("POITEM");
		JCoTable poItemX=tables.getTable("POITEMX");
		
		JCoTable poItemText=tables.getTable("POTEXTITEM");
		
		JCoTable messages=tables.getTable("RETURN");
		
		if(order.isTest()) {
			imports.setValue("TESTRUN", "X");
		}
		
		System.out.println("POHEADER fields: "+poHeader.getFieldCount());
		
		poHeader.setValue("COMP_CODE", order.getCompanyCode());
		poHeader.setValue("DOC_TYPE", order.getDocumentType());
		poHeader.setValue("PURCH_ORG", order.getPurchasingOrg());
		poHeader.setValue("PUR_GROUP", order.getPurchasingGroup());
		poHeader.setValue("DOC_DATE", order.getDocumentDate());
		poHeader.setValue("VENDOR", order.getVendor());
		poHeader.setValue("SUPPL_PLNT", "");
		poHeader.setValue("OUR_REF", order.getOurReference());
		poHeader.setValue("REF_1", order.getYourReference());
		
		poHeaderX.setValue("COMP_CODE", "X");
		poHeaderX.setValue("DOC_TYPE", "X");
		poHeaderX.setValue("PURCH_ORG", "X");
		poHeaderX.setValue("PUR_GROUP", "X");
		poHeaderX.setValue("DOC_DATE", "X");
		poHeaderX.setValue("VENDOR", "X");
		poHeaderX.setValue("SUPPL_PLNT", "X");
		poHeaderX.setValue("OUR_REF", "X");
		poHeaderX.setValue("REF_1", "X");
		
		for(POItemDTO item : order.getLineItems()) {
			poItem.appendRow();
			poItem.setValue("PO_ITEM", item.getItem());
			poItem.setValue("MATERIAL", item.getMaterial());
			poItem.setValue("PLANT", item.getPlant());
			poItem.setValue("STGE_LOC", item.getStorageLocation());
			poItem.setValue("QUANTITY", item.getQuantity());
			poItem.setValue("VAL_TYPE", item.getValuationType());
			poItem.setValue("TAX_CODE", item.getTaxCode());
			
			poItemX.appendRow();
			poItemX.setValue("PO_ITEM", item.getItem());
			poItemX.setValue("MATERIAL", "X");
			poItemX.setValue("PLANT", "X");
			poItemX.setValue("STGE_LOC", "X");
			poItemX.setValue("QUANTITY", "X");
			poItemX.setValue("VAL_TYPE", "X");
			poItemX.setValue("TAX_CODE", "X");
			
			for(POItemTextDTO textLine : item.getTextLines()) {
				poItemText.appendRow();
				poItemText.setValue("PO_ITEM",textLine.getItem());
				poItemText.setValue("TEXT_ID", textLine.getTextId());
				poItemText.setValue("TEXT_FORM", textLine.getTextForm());
				poItemText.setValue("TEXT_LINE", textLine.getTextLine());
			}
		}
		
		JCoContext.begin(destination);
		fCreatePO.execute(destination);
		
		response.setLines(createResponseLines(messages));
		
		String poNumber=exports.getString("EXPPURCHASEORDER");
		System.out.println("PO created: "+poNumber);
		if(poNumber!=null && !poNumber.isEmpty()) {
			fCommit.execute(destination);
			response.setPoNumber(Long.parseLong(poNumber));
		}
		
		return response;
	}
	
	private List<ResponseLineDTO> createResponseLines(JCoTable messages) {
	
		List<ResponseLineDTO> lines=new ArrayList<ResponseLineDTO>();
		
		do {
			ResponseLineDTO line=new ResponseLineDTO();
			line.setType(messages.getString("TYPE"));
			line.setNumber(messages.getInt("NUMBER"));
			line.setId(messages.getString("ID"));
			line.setRow(messages.getInt("ROW"));
			line.setMessage(messages.getString("MESSAGE"));
			line.setMessageVar1(messages.getString("MESSAGE_V1"));
			line.setMessageVar2(messages.getString("MESSAGE_V2"));
			line.setMessageVar3(messages.getString("MESSAGE_V3"));
			line.setMessageVar4(messages.getString("MESSAGE_V4"));
			lines.add(line);
		}
		while(messages.nextRow());
		
		return lines;
	}

	
}
