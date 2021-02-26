package org.rfc.material.worker;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.material.Description;
import org.rfc.material.ForecastParameters;
import org.rfc.material.Material;
import org.rfc.material.PlantData;
import org.rfc.material.SalesData;
import org.rfc.material.StorageLocationData;
import org.rfc.material.ValuationData;
import org.rfc.material.dto.CreateMaterialResultDTO;
import org.rfc.material.dto.FeedLineDTO;
import org.rfc.material.dto.ReturnMessageDTO;
import org.rfc.material.dto.WorkerDTO;
import org.springframework.boot.logging.LogLevel;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoTable;


public class CreateMaterialWorker extends Worker {
	
	private static final Logger logger=LogManager.getLogger(CreateMaterialWorker.class);
	
	public static final String RFC_NAME="BAPI_MATERIAL_SAVEREPLICA";
	
	private JCoTable tHEADDATA=null;
	private JCoTable tMATERIALDESCRIPTION=null;
	private JCoTable tCLIENTDATA=null;
	private JCoTable tCLIENTDATAX=null;
	private JCoTable tUNITSOFMEASURE=null;
	private JCoTable tUNITSOFMEASUREX=null;
	private JCoTable tSALESDATA=null;
	private JCoTable tSALESDATAX=null;
	private JCoTable tTAXCLASSIFICATIONS=null;
	private JCoTable tPLANTDATA=null;
	private JCoTable tPLANTDATAX=null;
	private JCoTable tSTORAGELOCATIONDATA=null;
	private JCoTable tSTORAGELOCATIONDATAX=null;
	private JCoTable tVALUATIONDATA=null;
	private JCoTable tVALUATIONDATAX=null;
	private JCoTable tFORECASTPARAMETERS=null;
	private JCoTable tFORECASTPARAMETERSX=null;
	private JCoTable tFORECASTVALUES=null;
	private JCoTable tEXTENSIONIN=null;
	private JCoTable tEXTENSIONINX=null;
	private JCoTable tRETURNMESSAGES=null;
	
	private BlockingQueue<CreateMaterialResultDTO> resultQueue;
	private Queue<Material> materialQueue;
	private int errorCount;
	private int successCount;
	private boolean testRun;
	private WorkerDTO dto;
	
	public CreateMaterialWorker(JCoDestination destination,boolean testRun) throws JCoException {
		super(destination);
		this.testRun=testRun;
		initialize();
	}
	
	public CreateMaterialWorker(int id,int runId,JCoDestination destination,boolean testRun) throws JCoException {
		super(id,runId,destination);
		this.testRun=testRun;
		initialize();
	}
	
	public CreateMaterialWorker(int id, int runId,List<Material> materials,BlockingQueue<CreateMaterialResultDTO> resultQueue,BlockingQueue<FeedLineDTO> feedQueue,JCoDestination destination,boolean testRun) throws JCoException {
		super(id,runId,destination,feedQueue);
		this.materialQueue=new LinkedList<Material>(materials);
		this.resultQueue=resultQueue;
		this.testRun=testRun;
		dto=new WorkerDTO(this.getId(),materialQueue.size(),0,0,this.status);
		initialize();
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public WorkerDTO getDto() {
		dto.setCurrentStatus(status);
		return dto;
	}

	public void setDto(WorkerDTO dto) {
		this.dto = dto;
	}

	@Override
	protected void doWork() throws JCoException,InterruptedException {
		Material m;
		logger.log(Level.INFO,"runId: "+runId+" workerId:"+id+"\tStarting work. Status: "+this.getStatus()+"\tDB queue size: "+materialQueue.size());
		while((m=materialQueue.poll())!=null) {
			CreateMaterialResultDTO result=createMaterial(m);
			resultQueue.add(result);
			feedQueue.add(new FeedLineDTO(new Timestamp(System.currentTimeMillis()),"Worker "+id,result.getMaterial()+" executed. Status: "+result.getStatus()+" S: "+result.getSuccessCount()+" W: "+result.getWarningCount()+" E: "+result.getErrorCount()));
			clearTables();
			if(Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
		}
		logger.log(Level.INFO,"runId: "+runId+" workerId:"+id+"\tJob done! Status: "+this.getStatus()+"\tDB queue size: "+materialQueue.size());
	}
	
	private void initialize() throws JCoException {
		initialize(RFC_NAME);
		
		function.getImportParameterList().setValue("NOAPPLLOG", "X");
		function.getImportParameterList().setValue("NOCHANGEDOC", "X");
		function.getImportParameterList().setValue("TESTRUN", (testRun==true ? "X" : " "));
		function.getImportParameterList().setValue("INPFLDCHECK", " ");
		
		tHEADDATA=function.getTableParameterList().getTable("HEADDATA");
		tCLIENTDATA=function.getTableParameterList().getTable("CLIENTDATA");
		tCLIENTDATAX=function.getTableParameterList().getTable("CLIENTDATAX");
		tMATERIALDESCRIPTION=function.getTableParameterList().getTable("MATERIALDESCRIPTION");
		tUNITSOFMEASURE=function.getTableParameterList().getTable("UNITSOFMEASURE");
		tUNITSOFMEASUREX=function.getTableParameterList().getTable("UNITSOFMEASUREX");
		tSALESDATA=function.getTableParameterList().getTable("SALESDATA");
		tSALESDATAX=function.getTableParameterList().getTable("SALESDATAX");
		tTAXCLASSIFICATIONS=function.getTableParameterList().getTable("TAXCLASSIFICATIONS");
		tPLANTDATA=function.getTableParameterList().getTable("PLANTDATA");
		tPLANTDATAX=function.getTableParameterList().getTable("PLANTDATAX");
		tSTORAGELOCATIONDATA=function.getTableParameterList().getTable("STORAGELOCATIONDATA");
		tSTORAGELOCATIONDATAX=function.getTableParameterList().getTable("STORAGELOCATIONDATAX");
		tVALUATIONDATA=function.getTableParameterList().getTable("VALUATIONDATA");
		tVALUATIONDATAX=function.getTableParameterList().getTable("VALUATIONDATAX");
		tFORECASTPARAMETERS=function.getTableParameterList().getTable("FORECASTPARAMETERS");
		tFORECASTPARAMETERSX=function.getTableParameterList().getTable("FORECASTPARAMETERSX");
		tEXTENSIONIN=function.getTableParameterList().getTable("EXTENSIONIN");
		tEXTENSIONINX=function.getTableParameterList().getTable("EXTENSIONINX");
		tRETURNMESSAGES=function.getTableParameterList().getTable("RETURNMESSAGES");
	}
	
	private CreateMaterialResultDTO createMaterial(Material m) throws JCoException{
		setHEADDATA(m);
		setCLIENTDATA(m);
		setMATERIALDESCRIPTION(m);
		setUNITSOFMEASURE(m);
		setSALESDATA(m);
		setTAXCLASSIFICATIONS(m);
		setPLANTDATA(m);
		setVALUATIONDATA(m);
		setSTORAGELOCATIONDATA(m);
		setFORECASTPARAMETERS(m);
		executeFunction();
		return handleReturnMessages(m);
		
	}
	
	private void setHEADDATA(Material m) {
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","INS");
		tHEADDATA.setValue("MATERIAL",m.getMaterialId());
		tHEADDATA.setValue("IND_SECTOR", m.getIndustrySector());
		tHEADDATA.setValue("MATL_TYPE",m.getType());
		tHEADDATA.setValue("BASIC_VIEW","X"); //K
		tHEADDATA.setValue("SALES_VIEW","X"); //V
		tHEADDATA.setValue("PURCHASE_VIEW","X"); //E
		tHEADDATA.setValue("MRP_VIEW","X");	//D
		tHEADDATA.setValue("STORAGE_VIEW", "X"); //L
		tHEADDATA.setValue("ACCOUNT_VIEW", "X"); //B
		tHEADDATA.setValue("FORECAST_VIEW", "X"); //P -missing
		tHEADDATA.setValue("QUALITY_VIEW", "X"); //Q
		tHEADDATA.setValue("COST_VIEW","X"); //G
	}
	
	private void setCLIENTDATA(Material m) {
		tCLIENTDATA.appendRow();
		tCLIENTDATA.setValue("FUNCTION", "INS");
		tCLIENTDATA.setValue("MATERIAL", m.getMaterialId());
		tCLIENTDATA.setValue("MATL_GROUP", m.getGroup());
		tCLIENTDATA.setValue("OLD_MAT_NO", m.getOldMaterialNumber());
		tCLIENTDATA.setValue("BASE_UOM", m.getBaseUom());
		tCLIENTDATA.setValue("ITEM_CAT", m.getGeneralItemCategoryGroup());
		tCLIENTDATA.setValue("PUR_STATUS", m.getCrossPlantMaterialStatus()); //01=design, 03=tuotanto
		tCLIENTDATA.setValue("NET_WEIGHT", m.getNetWeight());
		tCLIENTDATA.setValue("UNIT_OF_WT", m.getWeightUnit());
		tCLIENTDATA.setValue("STOR_CONDS", m.getStorageConditions());
		tCLIENTDATA.setValue("PUR_VALKEY", m.getPurchasingValueKey());
		tCLIENTDATA.setValue("TRANS_GRP", m.getTransportGroup());
		tCLIENTDATA.setValue("LABEL_TYPE",m.getLabelType());
		tCLIENTDATA.setValue("LABEL_FORM", m.getLabelForm());
		
		tCLIENTDATAX.appendRow();
		tCLIENTDATAX.setValue("FUNCTION", "INS");
		tCLIENTDATAX.setValue("MATERIAL", m.getMaterialId());
		tCLIENTDATAX.setValue("MATL_GROUP", "X");
		tCLIENTDATAX.setValue("OLD_MAT_NO", "X");
		tCLIENTDATAX.setValue("BASE_UOM", "X");
		tCLIENTDATAX.setValue("ITEM_CAT", "X");
		tCLIENTDATAX.setValue("PUR_STATUS", "X");
		tCLIENTDATAX.setValue("NET_WEIGHT", "X");
		tCLIENTDATAX.setValue("UNIT_OF_WT", "X");
		tCLIENTDATAX.setValue("STOR_CONDS", "X");
		tCLIENTDATAX.setValue("PUR_VALKEY", "X");
		tCLIENTDATAX.setValue("TRANS_GRP", "X");
		tCLIENTDATAX.setValue("LABEL_TYPE","X");
		tCLIENTDATAX.setValue("LABEL_FORM", "X");
	}
	
	private void setMATERIALDESCRIPTION(Material m) {
		for(Description desc : m.getDescriptionList()) {
			tMATERIALDESCRIPTION.appendRow();
			tMATERIALDESCRIPTION.setValue("FUNCTION", "INS");
			tMATERIALDESCRIPTION.setValue("MATERIAL", m.getMaterialId());
			tMATERIALDESCRIPTION.setValue("LANGU", desc.getLanguage());
			tMATERIALDESCRIPTION.setValue("MATL_DESC", desc.getDescription());
		}
	}
	
	private void setUNITSOFMEASURE(Material m) {
		tUNITSOFMEASURE.appendRow();
		tUNITSOFMEASURE.setValue("FUNCTION", "INS");
		tUNITSOFMEASURE.setValue("MATERIAL", m.getMaterialId());
		tUNITSOFMEASURE.setValue("ALT_UNIT", m.getBaseUom());
		tUNITSOFMEASURE.setValue("LENGTH", m.getLength());
		tUNITSOFMEASURE.setValue("WIDTH", m.getWidth());
		tUNITSOFMEASURE.setValue("HEIGHT", m.getHeight());
		tUNITSOFMEASURE.setValue("UNIT_DIM",m.getDimensionUnit());
		tUNITSOFMEASURE.setValue("VOLUMEUNIT", m.getVolumeUnit());
		tUNITSOFMEASURE.setValue("GROSS_WT", m.getGrossWeight());
		tUNITSOFMEASURE.setValue("UNIT_OF_WT", m.getWeightUnit());
		
		tUNITSOFMEASUREX.appendRow();
		tUNITSOFMEASUREX.setValue("FUNCTION", "INS");
		tUNITSOFMEASUREX.setValue("MATERIAL", m.getMaterialId());
		tUNITSOFMEASUREX.setValue("ALT_UNIT", m.getBaseUom());
		tUNITSOFMEASUREX.setValue("LENGTH", "X");
		tUNITSOFMEASUREX.setValue("WIDTH", "X");
		tUNITSOFMEASUREX.setValue("HEIGHT", "X");
		tUNITSOFMEASUREX.setValue("UNIT_DIM","X");
		tUNITSOFMEASUREX.setValue("VOLUMEUNIT", "X");
		tUNITSOFMEASUREX.setValue("GROSS_WT", "X");
		tUNITSOFMEASUREX.setValue("UNIT_OF_WT", "X");
	}
	
	private void setSALESDATA(Material m) {
		for(SalesData sd : m.getSalesDataList()) {
			tSALESDATA.appendRow();
			tSALESDATA.setValue("FUNCTION", "INS");
			tSALESDATA.setValue("MATERIAL", m.getMaterialId());
			tSALESDATA.setValue("SALES_ORG", sd.getSalesOrg());
			tSALESDATA.setValue("DISTR_CHAN", sd.getDistributionChannel());
			tSALESDATA.setValue("MATL_STATS", sd.getStatisticGroup());
			tSALESDATA.setValue("ITEM_CAT", sd.getItemCategoryGroup());
			tSALESDATA.setValue("ACCT_ASSGT",sd.getAccountAssignment());
			tSALESDATA.setValue("DELYG_PLNT", sd.getDeliveringPlant());
			
			tSALESDATAX.appendRow();
			tSALESDATAX.setValue("FUNCTION", "INS");
			tSALESDATAX.setValue("MATERIAL", m.getMaterialId());
			tSALESDATAX.setValue("SALES_ORG", sd.getSalesOrg());
			tSALESDATAX.setValue("DISTR_CHAN", sd.getDistributionChannel());
			tSALESDATAX.setValue("MATL_STATS", "X");
			tSALESDATAX.setValue("ITEM_CAT", "X");
			tSALESDATAX.setValue("ACCT_ASSGT","X");
			tSALESDATAX.setValue("DELYG_PLNT", "X");
		}
	}
	
	private void setTAXCLASSIFICATIONS(Material m) {
		tTAXCLASSIFICATIONS.appendRow();
		tTAXCLASSIFICATIONS.setValue("FUNCTION", "INS");
		tTAXCLASSIFICATIONS.setValue("MATERIAL", m.getMaterialId());
		tTAXCLASSIFICATIONS.setValue("DEPCOUNTRY", m.getDepartureCountry());
		tTAXCLASSIFICATIONS.setValue("TAX_TYPE_1", m.getTaxType1());
		tTAXCLASSIFICATIONS.setValue("TAXCLASS_1",m.getTaxClass1());
	}
	
	private void setPLANTDATA(Material m) {
		for(PlantData pd : m.getPlantDataList()) {
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "INS");
			tPLANTDATA.setValue("MATERIAL", m.getMaterialId());
			tPLANTDATA.setValue("PLANT", pd.getPlant());
			tPLANTDATA.setValue("PROFIT_CTR", pd.getProfitCenter());
			tPLANTDATA.setValue("PUR_GROUP",pd.getPurchasingGroup());
			tPLANTDATA.setValue("GR_PR_TIME",pd.getGrProcessingTime());
			tPLANTDATA.setValue("MRP_TYPE",pd.getMrpType());
			tPLANTDATA.setValue("REORDER_PT",pd.getReOrderPoint());
			tPLANTDATA.setValue("MRP_CTRLER", pd.getMrpController());
			tPLANTDATA.setValue("LOTSIZEKEY",pd.getLotSizingProcedure());
			tPLANTDATA.setValue("MINLOTSIZE", pd.getMinLotSize());
			tPLANTDATA.setValue("PROC_TYPE", pd.getProcurementType());
			tPLANTDATA.setValue("SPPROCTYPE", pd.getSpecialProcurement());
			tPLANTDATA.setValue("ISS_ST_LOC", pd.getIssueStorageLocation());
			tPLANTDATA.setValue("SLOC_EXPRC", pd.getStorageLocationForEP());
			tPLANTDATA.setValue("PLND_DELRY", pd.getPlannedDeliveryTime());
			tPLANTDATA.setValue("PERIOD_IND", pd.getPeriodIndicator());
			tPLANTDATA.setValue("AVAILCHECK", pd.getAvailabilityCheck());
			tPLANTDATA.setValue("DEP_REQ_ID", pd.getIndividualAndCollectiveReq());
			tPLANTDATA.setValue("LOADINGGRP", pd.getLoadingGroup());
			tPLANTDATA.setValue("NO_COSTING", (pd.getDoNotCost().isEmpty() ? " " : "X"));
			tPLANTDATA.setValue("AUTO_RESET", (pd.getAutoReset().isEmpty() ? " " : "X"));
			tPLANTDATA.setValue("COUNTRYORI", pd.getCountryOfOrigin());
			tPLANTDATA.setValue("COMM_CODE", pd.getCommodityCode());
			tPLANTDATA.setValue("SALES_VIEW","X"); //V
			tPLANTDATA.setValue("MRP_VIEW","X"); //D
			tPLANTDATA.setValue("PURCH_VIEW","X"); //E
			tPLANTDATA.setValue("STORAGE_VIEW","X"); //L
			tPLANTDATA.setValue("FORECAST_VIEW", "X"); //P
			tPLANTDATA.setValue("QUALITY_VIEW", "X"); //Q
			
			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "INS");
			tPLANTDATAX.setValue("MATERIAL", m.getMaterialId());
			tPLANTDATAX.setValue("PLANT", pd.getPlant());
			tPLANTDATAX.setValue("PROFIT_CTR", "X");
			tPLANTDATAX.setValue("PUR_GROUP", "X");
			tPLANTDATAX.setValue("GR_PR_TIME","X");
			tPLANTDATAX.setValue("MRP_TYPE","X");
			tPLANTDATAX.setValue("REORDER_PT","X");
			tPLANTDATAX.setValue("MRP_CTRLER", "X");
			tPLANTDATAX.setValue("LOTSIZEKEY","X");
			tPLANTDATAX.setValue("MINLOTSIZE", "X");
			tPLANTDATAX.setValue("PROC_TYPE", "X");
			tPLANTDATAX.setValue("SPPROCTYPE", "X");
			tPLANTDATAX.setValue("ISS_ST_LOC", "X");
			tPLANTDATAX.setValue("SLOC_EXPRC", "X");
			tPLANTDATAX.setValue("PLND_DELRY", "X");
			tPLANTDATAX.setValue("PERIOD_IND", "X");
			tPLANTDATAX.setValue("AVAILCHECK", "X");
			tPLANTDATAX.setValue("DEP_REQ_ID", "X");
			tPLANTDATAX.setValue("LOADINGGRP", "X");
			tPLANTDATAX.setValue("NO_COSTING", "X");
			tPLANTDATAX.setValue("AUTO_RESET", "X");
			tPLANTDATAX.setValue("COUNTRYORI", "X");
			tPLANTDATAX.setValue("COMM_CODE", "X");
		}
	}
	
	private void setVALUATIONDATA(Material m) {
		for(ValuationData vd : m.getValuationDataList()) {
			tVALUATIONDATA.appendRow();
			tVALUATIONDATA.setValue("FUNCTION", "INS");
			tVALUATIONDATA.setValue("MATERIAL", m.getMaterialId());
			tVALUATIONDATA.setValue("VAL_AREA", vd.getValuationArea());
			tVALUATIONDATA.setValue("PRICE_CTRL", vd.getPriceControl());
			tVALUATIONDATA.setValue("VAL_CLASS", vd.getValuationClass());
			tVALUATIONDATA.setValue("MOVING_PR", m.getPeriodicUnitPrice());
			tVALUATIONDATA.setValue("STD_PRICE", vd.getStandardPrice());
			tVALUATIONDATA.setValue("PRICE_UNIT", vd.getPriceUnit());
			tVALUATIONDATA.setValue("QTY_STRUCT", (vd.getCostWithQtyStructure().isEmpty() ? " " : "X"));
			tVALUATIONDATA.setValue("ORIG_MAT", (vd.getMaterialRelatedOrigin().isEmpty() ? " " : "X"));
			tVALUATIONDATA.setValue("ACCOUNT_VIEW", "X");
			tVALUATIONDATA.setValue("COST_VIEW", "X");
			
			tVALUATIONDATAX.appendRow();
			tVALUATIONDATAX.setValue("FUNCTION", "INS");
			tVALUATIONDATAX.setValue("MATERIAL", m.getMaterialId());
			tVALUATIONDATAX.setValue("VAL_AREA", vd.getValuationArea());
			tVALUATIONDATAX.setValue("PRICE_CTRL", "X");
			tVALUATIONDATAX.setValue("VAL_CLASS", "X");
			tVALUATIONDATAX.setValue("MOVING_PR","X");
			tVALUATIONDATAX.setValue("STD_PRICE", "X");
			tVALUATIONDATAX.setValue("PRICE_UNIT","X");
			tVALUATIONDATAX.setValue("QTY_STRUCT", "X");
			tVALUATIONDATAX.setValue("ORIG_MAT", "X");
		}
	}
	
	private void setSTORAGELOCATIONDATA(Material m) {
		for(StorageLocationData sl : m.getStorageLocationDataList()) {
			tSTORAGELOCATIONDATA.appendRow();
			tSTORAGELOCATIONDATA.setValue("FUNCTION","INS");
			tSTORAGELOCATIONDATA.setValue("MATERIAL", m.getMaterialId());
			tSTORAGELOCATIONDATA.setValue("PLANT",sl.getPlant());
			tSTORAGELOCATIONDATA.setValue("STGE_LOC", sl.getStorageLocation());
			tSTORAGELOCATIONDATA.setValue("MRP_VIEW","X");
			tSTORAGELOCATIONDATA.setValue("STORAGE_VIEW","X");
			
			tSTORAGELOCATIONDATAX.appendRow();
			tSTORAGELOCATIONDATAX.setValue("FUNCTION","INS");
			tSTORAGELOCATIONDATAX.setValue("MATERIAL", m.getMaterialId());
			tSTORAGELOCATIONDATAX.setValue("PLANT",sl.getPlant());
			tSTORAGELOCATIONDATAX.setValue("STGE_LOC", sl.getStorageLocation());	
		}
	}
	
	private void setFORECASTPARAMETERS(Material m) {
		for(ForecastParameters fp : m.getForecastParametersList()) {
			tFORECASTPARAMETERS.appendRow();
			tFORECASTPARAMETERS.setValue("FUNCTION","INS");
			tFORECASTPARAMETERS.setValue("MATERIAL", m.getMaterialId());
			tFORECASTPARAMETERS.setValue("PLANT", fp.getPlant());
			tFORECASTPARAMETERS.setValue("FORE_MODEL", fp.getForecastModel());
			tFORECASTPARAMETERS.setValue("HIST_VALS", fp.getHistoricalPeriods());
			tFORECASTPARAMETERS.setValue("FORE_PDS", fp.getForecastPeriods());
			tFORECASTPARAMETERS.setValue("INITIALIZE", (fp.getInitialize().isEmpty() ? " " : "X"));
			tFORECASTPARAMETERS.setValue("TRACKLIMIT", fp.getTrackingLimit());
			tFORECASTPARAMETERS.setValue("MODEL_SP", fp.getModelSelectionProc());
			
			tFORECASTPARAMETERSX.appendRow();
			tFORECASTPARAMETERSX.setValue("FUNCTION", "INS");
			tFORECASTPARAMETERSX.setValue("MATERIAL", m.getMaterialId());
			tFORECASTPARAMETERSX.setValue("PLANT", fp.getPlant());
			tFORECASTPARAMETERSX.setValue("FORE_MODEL", "X");
			tFORECASTPARAMETERSX.setValue("HIST_VALS", "X");
			tFORECASTPARAMETERSX.setValue("FORE_PDS", "X");
			tFORECASTPARAMETERSX.setValue("INITIALIZE", "X");
			tFORECASTPARAMETERSX.setValue("TRACKLIMIT", "X");
			tFORECASTPARAMETERSX.setValue("MODEL_SP", "X");
		}
	}
	
	private CreateMaterialResultDTO handleReturnMessages(Material m) {
		List<ReturnMessageDTO> messages=new ArrayList<ReturnMessageDTO>();
		do {
			ReturnMessageDTO message=new ReturnMessageDTO();
			message.setRunId(runId);
			message.setWorkerId(id);
			message.setMaterialId(m.getMaterialId());
			message.setNumber((String)tRETURNMESSAGES.getValue("NUMBER"));
			message.setType((String)tRETURNMESSAGES.getValue("TYPE"));
			message.setMessage((String) tRETURNMESSAGES.getValue("MESSAGE"));
			message.setRow(String.valueOf(tRETURNMESSAGES.getValue("ROW")));
			message.setMessageVariable1((String) tRETURNMESSAGES.getValue("MESSAGE_V1"));
			message.setMessageVariable2((String) tRETURNMESSAGES.getValue("MESSAGE_V2"));
			message.setMessageVariable3((String) tRETURNMESSAGES.getValue("MESSAGE_V3"));
			message.setMessageVariable4((String) tRETURNMESSAGES.getValue("MESSAGE_V4"));
			messages.add(message);
		}
		while(tRETURNMESSAGES.nextRow());
		CreateMaterialResultDTO result=new CreateMaterialResultDTO(runId,m.getMaterialId(),id,messages);
		if(result.getStatus()==1) {
			dto.addSuccess();
		}
		else {
			dto.addError();
		}
		return result;
	}
	
	private void clearTables() {
		tHEADDATA.clear();
		tMATERIALDESCRIPTION.clear();
		tUNITSOFMEASURE.clear();
		tUNITSOFMEASUREX.clear();
		tCLIENTDATA.clear();
		tCLIENTDATAX.clear();
		tSALESDATA.clear();
		tSALESDATAX.clear();
		tTAXCLASSIFICATIONS.clear();
		tPLANTDATA.clear();
		tPLANTDATAX.clear();
		tVALUATIONDATA.clear();
		tVALUATIONDATAX.clear();
		tSTORAGELOCATIONDATA.clear();
		tSTORAGELOCATIONDATAX.clear();
		tFORECASTPARAMETERS.clear();
		tFORECASTPARAMETERSX.clear();
		tRETURNMESSAGES.clear();
	}

}
