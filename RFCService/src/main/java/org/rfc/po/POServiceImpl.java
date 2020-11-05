package org.rfc.po;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rfc.exception.RFCException;
import org.rfc.orderconf.POConfirmationDTO;
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
	
	public ResponseDTO saveOrder(PurchaseOrderDTO order) throws RFCException{
		ResponseDTO response=null;
		response=createOrder(order);
		return response;
	}
	
	private ResponseDTO createOrder(PurchaseOrderDTO order) throws RFCException {
		
		JCoFunctionTemplate functionTemplate=null;
		JCoFunction fCreatePO=null;
		JCoFunction fCommit=null;
		JCoParameterList imports=null;
		JCoParameterList exports=null;
		JCoParameterList tables=null;
		
		JCoDestination destination=sapService.getDestination();
		
		try {
			functionTemplate=destination.getRepository().getFunctionTemplate("BAPI_PO_CREATE1");
			fCreatePO=functionTemplate.getFunction();
		}
		catch(JCoException e) {
			throw new RFCException(120,"Function BAPI_PO_CREATE1 not found!",e);
		}
		try {
			functionTemplate=destination.getRepository().getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
			fCommit=functionTemplate.getFunction();
		}
		catch(JCoException e) {
			throw new RFCException(120,"Function BAPI_TRANSACTION_COMMIT not found!",e);
		}
		
		imports=fCreatePO.getImportParameterList();
		exports=fCreatePO.getExportParameterList();
		tables=fCreatePO.getTableParameterList();
		
		JCoStructure poHeader=imports.getStructure("POHEADER");
		JCoStructure poHeaderX=imports.getStructure("POHEADERX");
		
		JCoTable poItem=tables.getTable("POITEM");
		JCoTable poItemX=tables.getTable("POITEMX");
		
		JCoTable poSchedule=tables.getTable("POSCHEDULE");
		JCoTable poScheduleX=tables.getTable("POSCHEDULEX");
		
		//JCoTable poCond=tables.getTable("POCOND");
		//JCoTable poCondX=tables.getTable("POCONDX");
		
		JCoTable poItemText=tables.getTable("POTEXTITEM");
		JCoTable poAddressDelivery=tables.getTable("POADDRDELIVERY");
		
		JCoTable messages=tables.getTable("RETURN");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		
		if(order.isTest()) {
			imports.setValue("TESTRUN", "X");
		}
		
		//No price calculation based on previous POs
		imports.setValue("NO_PRICE_FROM_PO", "X");
		
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
			poItem.setValue("NET_PRICE", item.getNetPrice());
			poItem.setValue("VAL_TYPE", item.getValuationType());
			poItem.setValue("TAX_CODE", item.getTaxCode());
			
			poItemX.appendRow();
			poItemX.setValue("PO_ITEM", item.getItem());
			poItemX.setValue("MATERIAL", "X");
			poItemX.setValue("PLANT", "X");
			poItemX.setValue("STGE_LOC", "X");
			poItemX.setValue("QUANTITY", "X");
			poItemX.setValue("NET_PRICE", "X");
			poItemX.setValue("VAL_TYPE", "X");
			poItemX.setValue("TAX_CODE", "X");
			
			poSchedule.appendRow();
			poSchedule.setValue("PO_ITEM", item.getItem());
			poSchedule.setValue("SCHED_LINE", "0001");
			poSchedule.setValue("DELIVERY_DATE",formatter.format(item.getDeliveryDate()));
			
			poScheduleX.appendRow();
			poScheduleX.setValue("PO_ITEM", item.getItem());
			poScheduleX.setValue("SCHED_LINE", "0001");
			poScheduleX.setValue("DELIVERY_DATE", "X");
			
//			poCond.appendRow();
//			poCond.setValue("ITM_NUMBER", item.getItem());
//			poCond.setValue("COND_ST_NO", "001");
//			poCond.setValue("COND_TYPE", "PB00");
//			poCond.setValue("COND_VALUE", item.getNetPrice());
//			poCond.setValue("CURRENCY", "EUR");
//			poCond.setValue("CURRENCY_ISO", "EUR");
//			
//			poCondX.appendRow();
//			poCondX.setValue("ITM_NUMBER", item.getItem());
//			poCondX.setValue("COND_ST_NO", "001");
//			poCondX.setValue("COND_TYPE", "X");
//			poCondX.setValue("COND_VALUE", "X");
//			poCondX.setValue("CURRENCY", "X");
//			poCondX.setValue("CURRENCY_ISO", "X");
			
			for(POItemTextDTO textLine : item.getTextLines()) {
				poItemText.appendRow();
				poItemText.setValue("PO_ITEM",textLine.getItem());
				poItemText.setValue("TEXT_ID", textLine.getTextId());
				poItemText.setValue("TEXT_FORM", textLine.getTextForm());
				poItemText.setValue("TEXT_LINE", textLine.getTextLine());
			}
			
			if(item.getAddress()!=null) {
				PODeliveryAddressDTO address=item.getAddress();
				poAddressDelivery.appendRow();
				poAddressDelivery.setValue("PO_ITEM", item.getItem());
				poAddressDelivery.setValue("FORMOFADDR", address.getTitle());
				poAddressDelivery.setValue("NAME", address.getName1());
				poAddressDelivery.setValue("NAME_2", address.getName2());
				poAddressDelivery.setValue("STREET", address.getStreet());
				poAddressDelivery.setValue("HOUSE_NO", address.getHouseNumber());
				poAddressDelivery.setValue("POSTL_COD1", address.getPostCode());
				poAddressDelivery.setValue("CITY", address.getCity());
				poAddressDelivery.setValue("COUNTRY",address.getCountryCode());
			}
			
		}
		
		try {
			JCoContext.begin(destination);
			fCreatePO.execute(destination);
			fCommit.execute(destination);
		}
		catch(JCoException e) {
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
		
		ResponseDTO response=new ResponseDTO();
		response.setMetaData(order.getMetaData());
		response.setTest(order.isTest());
		response.setLines(createResponseLines(messages));
		
		String poNumber=exports.getString("EXPPURCHASEORDER");
		System.out.println("PO created: "+poNumber);
		if(poNumber!=null && !poNumber.isEmpty()) {
			
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
	
	public PurchaseOrderDTO getDetails(long poId) throws RFCException {
		
		PurchaseOrderDTO order=null;
		
		JCoFunctionTemplate functionTemplate=null;
		JCoFunction fGetDetail=null;
		JCoParameterList imports=null;
		JCoParameterList exports=null;
		JCoParameterList tables=null;
		
		JCoDestination destination=sapService.getDestination();
		
		try {
			functionTemplate=destination.getRepository().getFunctionTemplate("BAPI_PO_GETDETAIL1");		
		}
		catch(JCoException e) {
			e.printStackTrace();
			throw new RFCException(120,"Function BAPI_PO_GETDETAIL1 not found!",e);
		}
		
		fGetDetail=functionTemplate.getFunction();
		imports=fGetDetail.getImportParameterList();
		exports=fGetDetail.getExportParameterList();
		tables=fGetDetail.getTableParameterList();
		
		JCoStructure hdr=exports.getStructure("POHEADER");
		JCoTable it=tables.getTable("POITEM");
		JCoTable ct=tables.getTable("POCONFIRMATION");
		JCoTable rt=tables.getTable("RETURN");
		
		imports.setValue("PURCHASEORDER", poId);
		
		
		try {
			JCoContext.begin(destination);
			fGetDetail.execute(destination);
		}
		catch(JCoException e){
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
		
		if(rt.getNumRows()>0) {
			String message=rt.getString("MESSAGE");
			throw new RFCException(310,message);
		}
		
		order=createPurchaseOrder(hdr,it,ct);
		
		return order;
	}
	
	private PurchaseOrderDTO createPurchaseOrder(JCoStructure hdr,JCoTable items,JCoTable confirmations) {
		PurchaseOrderDTO order=new PurchaseOrderDTO();
		order.setId(hdr.getLong("PO_NUMBER"));
		order.setCompanyCode(hdr.getString("COMP_CODE"));
		order.setDocumentType(hdr.getString("DOC_TYPE"));
		order.setStatus(hdr.getString("STATUS"));
		order.setCreatedDate(new Date(hdr.getDate("CREAT_DATE").getTime()));
		order.setCreatedBy(hdr.getString("CREATED_BY"));
		order.setVendor(hdr.getString("VENDOR"));
		order.setPurchasingOrg(hdr.getString("PURCH_ORG"));
		order.setPurchasingGroup(hdr.getString("PUR_GROUP"));
		order.setDocumentDate(new Date(hdr.getDate("DOC_DATE").getTime()));
		
		Map<Integer,POItemDTO> itemMap=createItemMap(items);
		
		if(confirmations.isEmpty()==false) {
			Map<Integer,List<POConfirmationDTO>> confMap=createConfirmationsMap(confirmations);
			
			for(Integer itemNum : itemMap.keySet()) {
				POItemDTO item=itemMap.get(itemNum);
				item.setConfirmations(confMap.get(itemNum));
			}
		}
		
		List<Integer> sortedKeys=new ArrayList<Integer>(itemMap.keySet());
		Collections.sort(sortedKeys);
		
		List<POItemDTO> itemList=new ArrayList<POItemDTO>();
		
		for(Integer itemNum : sortedKeys) {
			itemList.add(itemMap.get(itemNum));
		}
		
		order.setLineItems(itemList);
	
		return order;
	}
	
	private Map<Integer,POItemDTO> createItemMap(JCoTable it){
		Map<Integer,POItemDTO> itemMap=new HashMap<Integer,POItemDTO>();
		do {
			POItemDTO item=new POItemDTO();
			item.setItem(it.getInt("PO_ITEM"));
			item.setMaterial(it.getString("MATERIAL"));
			item.setQuantity(it.getDouble("QUANTITY"));
			item.setPlant(it.getString("PLANT"));
			item.setStorageLocation(it.getString("STGE_LOC"));
			item.setTaxCode(it.getString("TAX_CODE"));
			itemMap.put(item.getItem(), item);
		}
		while(it.nextRow());
		
		return itemMap;
	}
	
	private Map<Integer,List<POConfirmationDTO>> createConfirmationsMap(JCoTable ct){
		Map<Integer,List<POConfirmationDTO>> confMap=new HashMap<Integer,List<POConfirmationDTO>>();
		do {
			int itemNum=ct.getInt("PO_ITEM");
			List<POConfirmationDTO> confList=confMap.get(itemNum);
			if(confList==null) {
				confList=new ArrayList<POConfirmationDTO>();
				confMap.put(itemNum, confList);
			}
			POConfirmationDTO conf=new POConfirmationDTO();
			conf.setItem(itemNum);
			conf.setControlKey(ct.getString("CONF_SER"));
			conf.setType(ct.getString("CONF_TYPE"));
			conf.setName(ct.getString("CONF_NAME"));
			conf.setQuantity(ct.getDouble("QUANTITY"));
			conf.setDeliveryDate(new Date(ct.getDate("DELIV_DATE").getTime()));
			conf.setReferenceDoc(ct.getString("EXT_DOC_LONG"));
			confList.add(conf);
		}
		while(ct.nextRow());
		
		return confMap;
	}
	
	

	
}
