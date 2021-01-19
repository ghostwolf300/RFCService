package org.rfc.material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {
	
	private Map<String,List<BAPIField>> fieldMap;

	public Map<String,List<BAPIField>> getFieldMap(){
		if(fieldMap==null) {
			initFieldMap();
		}
		return fieldMap;
	}
	
	private void initFieldMap() {
		fieldMap=new HashMap<String,List<BAPIField>>();
		
		List<BAPIField> fieldsHEADDATA=new ArrayList<BAPIField>();
		fieldsHEADDATA.add(new BAPIField("MATERIAL",18));
		fieldsHEADDATA.add(new BAPIField("IND_SECTOR",1));
		fieldsHEADDATA.add(new BAPIField("MATL_TYPE",4));
		fieldMap.put("HEADDATA", fieldsHEADDATA);
		
		List<BAPIField> fieldsCLIENTDATA=new ArrayList<BAPIField>();
		fieldsCLIENTDATA.add(new BAPIField("MATL_GROUP",9));
		fieldsCLIENTDATA.add(new BAPIField("OLD_MAT_NO",18));
		fieldsCLIENTDATA.add(new BAPIField("BASE_UOM",3));
		fieldsCLIENTDATA.add(new BAPIField("ITEM_CAT",4));
		fieldsCLIENTDATA.add(new BAPIField("PUR_STATUS",2));
		fieldsCLIENTDATA.add(new BAPIField("NET_WEIGHT",13));
		fieldsCLIENTDATA.add(new BAPIField("UNIT_OF_WT",3));
		fieldsCLIENTDATA.add(new BAPIField("STOR_CONDS",2));
		fieldsCLIENTDATA.add(new BAPIField("PUR_VALKEY",4));
		fieldsCLIENTDATA.add(new BAPIField("TRANS_GRP",4));
		fieldsCLIENTDATA.add(new BAPIField("LABEL_TYPE",2));
		fieldsCLIENTDATA.add(new BAPIField("LABEL_FORM",2));
		fieldMap.put("CLIENTDATA", fieldsCLIENTDATA);
		
		List<BAPIField> fieldsMATERIALDESCRIPTION=new ArrayList<BAPIField>();
		fieldsMATERIALDESCRIPTION.add(new BAPIField("LANGU",1));
		fieldsMATERIALDESCRIPTION.add(new BAPIField("MATL_DESC",40));
		fieldMap.put("MATERIALDESCRIPTION", fieldsMATERIALDESCRIPTION);
		
		List<BAPIField> fieldsUNITSOFMEASURE=new ArrayList<BAPIField>();
		fieldsUNITSOFMEASURE.add(new BAPIField("LENGTH",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("WIDTH",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("HEIGHT",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("UNIT_DIM",3));
		fieldsUNITSOFMEASURE.add(new BAPIField("VOLUMEUNIT",3));
		fieldsUNITSOFMEASURE.add(new BAPIField("GROSS_WT",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("UNIT_OF_WT",3));
		fieldMap.put("UNITSOFMEASURE",fieldsUNITSOFMEASURE);
		
		List<BAPIField> fieldsSALESDATA=new ArrayList<BAPIField>();
		fieldsSALESDATA.add(new BAPIField("SALES_ORG",4));
		fieldsSALESDATA.add(new BAPIField("DISTR_CHAN",2));
		fieldsSALESDATA.add(new BAPIField("MATL_STATS",1));
		fieldsSALESDATA.add(new BAPIField("ITEM_CAT",4));
		fieldsSALESDATA.add(new BAPIField("ACCT_ASSGT",2));
		fieldsSALESDATA.add(new BAPIField("DELYG_PLNT",4));
		fieldMap.put("SALESDATA",fieldsSALESDATA);
		
		List<BAPIField> fieldsTAXCLASSIFICATIONS=new ArrayList<BAPIField>();
		fieldsTAXCLASSIFICATIONS.add(new BAPIField("DEPCOUNTRY",3));
		fieldsTAXCLASSIFICATIONS.add(new BAPIField("TAX_TYPE_1",4));
		fieldsTAXCLASSIFICATIONS.add(new BAPIField("TAXCLASS_1",1));
		fieldMap.put("TAXCLASSIFICATIONS",fieldsTAXCLASSIFICATIONS);
		
		List<BAPIField> fieldsPLANTDATA=new ArrayList<BAPIField>();
		fieldsPLANTDATA.add(new BAPIField("PLANT",4));
		fieldsPLANTDATA.add(new BAPIField("PROFIT_CTR",10));
		fieldsPLANTDATA.add(new BAPIField("PUR_GROUP",3));
		fieldsPLANTDATA.add(new BAPIField("GR_PR_TIME",3));
		fieldsPLANTDATA.add(new BAPIField("MRP_TYPE",2));
		fieldsPLANTDATA.add(new BAPIField("REORDER_PT",13));
		fieldsPLANTDATA.add(new BAPIField("MRP_CTRLER",3));
		fieldsPLANTDATA.add(new BAPIField("LOTSIZEKEY",2));
		fieldsPLANTDATA.add(new BAPIField("MINLOTSIZE",13));
		fieldsPLANTDATA.add(new BAPIField("PROC_TYPE",1));
		fieldsPLANTDATA.add(new BAPIField("SPPROCTYPE",2));
		fieldsPLANTDATA.add(new BAPIField("ISS_ST_LOC",4));
		fieldsPLANTDATA.add(new BAPIField("SLOC_EXPRC",4));
		fieldsPLANTDATA.add(new BAPIField("PLND_DELRY",3));
		fieldsPLANTDATA.add(new BAPIField("PERIOD_IND",1));
		fieldsPLANTDATA.add(new BAPIField("AVAILCHECK",2));
		fieldsPLANTDATA.add(new BAPIField("DEP_REQ_ID",1));
		fieldsPLANTDATA.add(new BAPIField("LOADINGGRP",4));
		fieldsPLANTDATA.add(new BAPIField("NO_COSTING",1));
		fieldsPLANTDATA.add(new BAPIField("AUTO_RESET",1));
		fieldsPLANTDATA.add(new BAPIField("COUNTRYORI",3));
		fieldsPLANTDATA.add(new BAPIField("COMM_CODE",17));
		fieldMap.put("PLANTDATA",fieldsPLANTDATA);
		
		List<BAPIField> fieldsVALUATIONDATA=new ArrayList<BAPIField>();
		fieldsVALUATIONDATA.add(new BAPIField("VAL_AREA",4));
		fieldsVALUATIONDATA.add(new BAPIField("PRICE_CTRL",1));
		fieldsVALUATIONDATA.add(new BAPIField("VAL_CLASS",4));
		fieldsVALUATIONDATA.add(new BAPIField("MOVING_PR",23));
		fieldsVALUATIONDATA.add(new BAPIField("STD_PRICE",23));
		fieldsVALUATIONDATA.add(new BAPIField("PRICE_UNIT",5));
		fieldsVALUATIONDATA.add(new BAPIField("QTY_STRUCT",1));
		fieldsVALUATIONDATA.add(new BAPIField("ORIG_MAT",1));
		fieldMap.put("VALUATIONDATA", fieldsVALUATIONDATA);
		
		List<BAPIField> fieldsSTORAGELOCATIONDATA=new ArrayList<BAPIField>();
		fieldsSTORAGELOCATIONDATA.add(new BAPIField("PLANT",4));
		fieldsSTORAGELOCATIONDATA.add(new BAPIField("STGE_LOC",4));
		fieldMap.put("STORAGELOCATIONDATA", fieldsSTORAGELOCATIONDATA);
		
		List<BAPIField> fieldsFORECASTPARAMETERS=new ArrayList<BAPIField>();
		fieldsFORECASTPARAMETERS.add(new BAPIField("PLANT",4));
		fieldsFORECASTPARAMETERS.add(new BAPIField("FORE_MODEL",1));
		fieldsFORECASTPARAMETERS.add(new BAPIField("HIST_VALS",3));
		fieldsFORECASTPARAMETERS.add(new BAPIField("FORE_PDS",3));
		fieldsFORECASTPARAMETERS.add(new BAPIField("INITIALIZE",1));
		fieldsFORECASTPARAMETERS.add(new BAPIField("TRACKLIMIT",5));
		fieldsFORECASTPARAMETERS.add(new BAPIField("MODEL_SP",1));
		fieldMap.put("FORECASTPARAMETERS", fieldsFORECASTPARAMETERS);
		
	}
	
}
