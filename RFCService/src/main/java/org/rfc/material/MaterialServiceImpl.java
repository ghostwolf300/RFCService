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
		fieldsHEADDATA.add(new BAPIField("MATERIAL","material",18));
		fieldsHEADDATA.add(new BAPIField("IND_SECTOR","indSector",1));
		fieldsHEADDATA.add(new BAPIField("MATL_TYPE","matlType",4));
		fieldMap.put("HEADDATA", fieldsHEADDATA);
		
		List<BAPIField> fieldsCLIENTDATA=new ArrayList<BAPIField>();
		fieldsCLIENTDATA.add(new BAPIField("MATL_GROUP","matlGroup",9));
		fieldsCLIENTDATA.add(new BAPIField("OLD_MAT_NO","oldMatNo",18));
		fieldsCLIENTDATA.add(new BAPIField("BASE_UOM","baseUom",3));
		fieldsCLIENTDATA.add(new BAPIField("ITEM_CAT","itemCat",4));
		fieldsCLIENTDATA.add(new BAPIField("PUR_STATUS","purStatus",2));
		fieldsCLIENTDATA.add(new BAPIField("NET_WEIGHT","netWeight",13));
		fieldsCLIENTDATA.add(new BAPIField("UNIT_OF_WT","unitOfWt",3));
		fieldsCLIENTDATA.add(new BAPIField("STOR_CONDS","storConds",2));
		fieldsCLIENTDATA.add(new BAPIField("PUR_VALKEY","purValKey",4));
		fieldsCLIENTDATA.add(new BAPIField("TRANS_GRP","transGrp",4));
		fieldsCLIENTDATA.add(new BAPIField("LABEL_TYPE","labelType",2));
		fieldsCLIENTDATA.add(new BAPIField("LABEL_FORM","labelForm",2));
		fieldMap.put("CLIENTDATA", fieldsCLIENTDATA);
		
		List<BAPIField> fieldsMATERIALDESCRIPTION=new ArrayList<BAPIField>();
		fieldsMATERIALDESCRIPTION.add(new BAPIField("LANGU","langu",1));
		fieldsMATERIALDESCRIPTION.add(new BAPIField("MATL_DESC","matlDesc",40));
		fieldMap.put("MATERIALDESCRIPTION", fieldsMATERIALDESCRIPTION);
		
		List<BAPIField> fieldsUNITSOFMEASURE=new ArrayList<BAPIField>();
		fieldsUNITSOFMEASURE.add(new BAPIField("LENGTH","length",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("WIDTH","width",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("HEIGHT","height",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("UNIT_DIM","unitDim",3));
		fieldsUNITSOFMEASURE.add(new BAPIField("VOLUMEUNIT","volumeUnit",3));
		fieldsUNITSOFMEASURE.add(new BAPIField("GROSS_WT","grossWt",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("UNIT_OF_WT","unitOfWt",3));
		fieldMap.put("UNITSOFMEASURE",fieldsUNITSOFMEASURE);
		
		List<BAPIField> fieldsSALESDATA=new ArrayList<BAPIField>();
		fieldsSALESDATA.add(new BAPIField("SALES_ORG","salesOrg",4));
		fieldsSALESDATA.add(new BAPIField("DISTR_CHAN","distrChan",2));
		fieldsSALESDATA.add(new BAPIField("MATL_STATS","matlStats",1));
		fieldsSALESDATA.add(new BAPIField("ITEM_CAT","itemCat",4));
		fieldsSALESDATA.add(new BAPIField("ACCT_ASSGT","acctAssgt",2));
		fieldsSALESDATA.add(new BAPIField("DELYG_PLNT","delygPlnt",4));
		fieldMap.put("SALESDATA",fieldsSALESDATA);
		
		List<BAPIField> fieldsTAXCLASSIFICATIONS=new ArrayList<BAPIField>();
		fieldsTAXCLASSIFICATIONS.add(new BAPIField("DEPCOUNTRY","depCountry",3));
		fieldsTAXCLASSIFICATIONS.add(new BAPIField("TAX_TYPE_1","taxType1",4));
		fieldsTAXCLASSIFICATIONS.add(new BAPIField("TAXCLASS_1","taxClass1",1));
		fieldMap.put("TAXCLASSIFICATIONS",fieldsTAXCLASSIFICATIONS);
		
		List<BAPIField> fieldsPLANTDATA=new ArrayList<BAPIField>();
		fieldsPLANTDATA.add(new BAPIField("PLANT","plant",4));
		fieldsPLANTDATA.add(new BAPIField("PROFIT_CTR","profitCtr",10));
		fieldsPLANTDATA.add(new BAPIField("PUR_GROUP","purGroup",3));
		fieldsPLANTDATA.add(new BAPIField("GR_PR_TIME","grPrTime",3));
		fieldsPLANTDATA.add(new BAPIField("MRP_TYPE","mrpType",2));
		fieldsPLANTDATA.add(new BAPIField("REORDER_PT","reorderPt",13));
		fieldsPLANTDATA.add(new BAPIField("MRP_CTRLER","mrpCtrler",3));
		fieldsPLANTDATA.add(new BAPIField("LOTSIZEKEY","lotSizeKey",2));
		fieldsPLANTDATA.add(new BAPIField("MINLOTSIZE","minLotSize",13));
		fieldsPLANTDATA.add(new BAPIField("PROC_TYPE","procType",1));
		fieldsPLANTDATA.add(new BAPIField("SPPROCTYPE","spProcType",2));
		fieldsPLANTDATA.add(new BAPIField("ISS_ST_LOC","issStLoc",4));
		fieldsPLANTDATA.add(new BAPIField("SLOC_EXPRC","slocExprc",4));
		fieldsPLANTDATA.add(new BAPIField("PLND_DELRY","plndDelry",3));
		fieldsPLANTDATA.add(new BAPIField("PERIOD_IND","periodInd",1));
		fieldsPLANTDATA.add(new BAPIField("AVAILCHECK","availCheck",2));
		fieldsPLANTDATA.add(new BAPIField("DEP_REQ_ID","depReqId",1));
		fieldsPLANTDATA.add(new BAPIField("LOADINGGRP","loadingGrp",4));
		fieldsPLANTDATA.add(new BAPIField("NO_COSTING","noCosting",1));
		fieldsPLANTDATA.add(new BAPIField("AUTO_RESET","autoReset",1));
		fieldsPLANTDATA.add(new BAPIField("COUNTRYORI","countryOri",3));
		fieldsPLANTDATA.add(new BAPIField("COMM_CODE","commCode",17));
		fieldMap.put("PLANTDATA",fieldsPLANTDATA);
		
		List<BAPIField> fieldsVALUATIONDATA=new ArrayList<BAPIField>();
		fieldsVALUATIONDATA.add(new BAPIField("VAL_AREA","valArea",4));
		fieldsVALUATIONDATA.add(new BAPIField("PRICE_CTRL","priceCtrl",1));
		fieldsVALUATIONDATA.add(new BAPIField("VAL_CLASS","valClass",4));
		fieldsVALUATIONDATA.add(new BAPIField("MOVING_PR","movingPr",23));
		fieldsVALUATIONDATA.add(new BAPIField("STD_PRICE","stdPrice",23));
		fieldsVALUATIONDATA.add(new BAPIField("PRICE_UNIT","priceUnit",5));
		fieldsVALUATIONDATA.add(new BAPIField("QTY_STRUCT","qtyStruct",1));
		fieldsVALUATIONDATA.add(new BAPIField("ORIG_MAT","origMat",1));
		fieldMap.put("VALUATIONDATA", fieldsVALUATIONDATA);
		
		List<BAPIField> fieldsSTORAGELOCATIONDATA=new ArrayList<BAPIField>();
		fieldsSTORAGELOCATIONDATA.add(new BAPIField("PLANT","plant",4));
		fieldsSTORAGELOCATIONDATA.add(new BAPIField("STGE_LOC","stgeLoc",4));
		fieldMap.put("STORAGELOCATIONDATA", fieldsSTORAGELOCATIONDATA);
		
		List<BAPIField> fieldsFORECASTPARAMETERS=new ArrayList<BAPIField>();
		fieldsFORECASTPARAMETERS.add(new BAPIField("PLANT","plant",4));
		fieldsFORECASTPARAMETERS.add(new BAPIField("FORE_MODEL","foreModel",1));
		fieldsFORECASTPARAMETERS.add(new BAPIField("HIST_VALS","histVals",3));
		fieldsFORECASTPARAMETERS.add(new BAPIField("FORE_PDS","forePds",3));
		fieldsFORECASTPARAMETERS.add(new BAPIField("INITIALIZE","initialize",1));
		fieldsFORECASTPARAMETERS.add(new BAPIField("TRACKLIMIT","trackLimit",5));
		fieldsFORECASTPARAMETERS.add(new BAPIField("MODEL_SP","modelSp",1));
		fieldMap.put("FORECASTPARAMETERS", fieldsFORECASTPARAMETERS);
		
	}
	
}
