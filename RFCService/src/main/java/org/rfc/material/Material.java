package org.rfc.material;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Id;

import org.rfc.material.dto.FieldValueDTO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Material implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@Indexed(unique=false)
	private int runId=-1;
	@Indexed(unique=true)
	private int rowNumber;
	@Indexed(unique=true)
	private String materialId;
	private String type;
	private String industrySector;
	private String oldMaterialNumber;
	private String baseUom;
	private String group;
	private String generalItemCategoryGroup;
	private String crossPlantMaterialStatus;
	private String weightUnit;
	private double grossWeight;
	private double netWeight;
	private String storageConditions;
	private String purchasingValueKey;
	private String transportGroup;
	private double length;
	private double width;
	private double height;
	private String dimensionUnit;
	private String volumeUnit;
	private String labelType;
	private String labelForm;
	private double periodicUnitPrice;
	//-------TAXCLASSIFICATIONS----------------
	private String departureCountry;
	private String taxType1;
	private String taxClass1;
	private List<Description> descriptionList;
	private List<SalesData> salesDataList;
	private List<PlantData> plantDataList;
	private List<ValuationData> valuationDataList;
	private List<StorageLocationData> storageLocationDataList;
	private List<ForecastParameters> forecastParametersList;
	
	public Material() {
		super();
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndustrySector() {
		return industrySector;
	}

	public void setIndustrySector(String industrySector) {
		this.industrySector = industrySector;
	}

	public String getOldMaterialNumber() {
		return oldMaterialNumber;
	}

	public void setOldMaterialNumber(String oldMaterialNumber) {
		this.oldMaterialNumber = oldMaterialNumber;
	}

	public String getBaseUom() {
		return baseUom;
	}

	public void setBaseUom(String baseUom) {
		this.baseUom = baseUom;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getGeneralItemCategoryGroup() {
		return generalItemCategoryGroup;
	}

	public void setGeneralItemCategoryGroup(String generalItemCategoryGroup) {
		this.generalItemCategoryGroup = generalItemCategoryGroup;
	}

	public String getCrossPlantMaterialStatus() {
		return crossPlantMaterialStatus;
	}

	public void setCrossPlantMaterialStatus(String crossPlantMaterialStatus) {
		this.crossPlantMaterialStatus = crossPlantMaterialStatus;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(double netWeight) {
		this.netWeight = netWeight;
	}

	public String getStorageConditions() {
		return storageConditions;
	}

	public void setStorageConditions(String storageConditions) {
		this.storageConditions = storageConditions;
	}

	public String getPurchasingValueKey() {
		return purchasingValueKey;
	}

	public void setPurchasingValueKey(String purchasingValueKey) {
		this.purchasingValueKey = purchasingValueKey;
	}

	public String getTransportGroup() {
		return transportGroup;
	}

	public void setTransportGroup(String transportGroup) {
		this.transportGroup = transportGroup;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getDimensionUnit() {
		return dimensionUnit;
	}

	public void setDimensionUnit(String dimensionUnit) {
		this.dimensionUnit = dimensionUnit;
	}

	public String getVolumeUnit() {
		return volumeUnit;
	}

	public void setVolumeUnit(String volumeUnit) {
		this.volumeUnit = volumeUnit;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getLabelForm() {
		return labelForm;
	}

	public void setLabelForm(String labelForm) {
		this.labelForm = labelForm;
	}

	public double getPeriodicUnitPrice() {
		return periodicUnitPrice;
	}

	public void setPeriodicUnitPrice(double periodicUnitPrice) {
		this.periodicUnitPrice = periodicUnitPrice;
	}

	public String getDepartureCountry() {
		return departureCountry;
	}

	public void setDepartureCountry(String departureCountry) {
		this.departureCountry = departureCountry;
	}

	public String getTaxType1() {
		return taxType1;
	}

	public void setTaxType1(String taxType1) {
		this.taxType1 = taxType1;
	}

	public String getTaxClass1() {
		return taxClass1;
	}

	public void setTaxClass1(String taxClass1) {
		this.taxClass1 = taxClass1;
	}

	public List<Description> getDescriptionList() {
		return descriptionList;
	}

	public void setDescriptionList(List<Description> descriptionList) {
		this.descriptionList = descriptionList;
	}

	public List<SalesData> getSalesDataList() {
		return salesDataList;
	}

	public void setSalesDataList(List<SalesData> salesDataList) {
		this.salesDataList = salesDataList;
	}

	public List<PlantData> getPlantDataList() {
		return plantDataList;
	}

	public void setPlantDataList(List<PlantData> plantDataList) {
		this.plantDataList = plantDataList;
	}

	public List<ValuationData> getValuationDataList() {
		return valuationDataList;
	}

	public void setValuationDataList(List<ValuationData> valuationDataList) {
		this.valuationDataList = valuationDataList;
	}

	public List<StorageLocationData> getStorageLocationDataList() {
		return storageLocationDataList;
	}

	public void setStorageLocationDataList(List<StorageLocationData> storageLocationDataList) {
		this.storageLocationDataList = storageLocationDataList;
	}

	public List<ForecastParameters> getForecastParametersList() {
		return forecastParametersList;
	}

	public void setForecastParametersList(List<ForecastParameters> forecastParametersList) {
		this.forecastParametersList = forecastParametersList;
	}
	
	public void setRowData(String structure,Map<String,FieldValueDTO> rowData) {
		if(structure.equals("HEADDATA")) {
			addHeadData(rowData);
		}
		else if(structure.equals("CLIENTDATA")) {
			addClientData(rowData);
		}
		else if(structure.equals("MATERIALDESCRIPTION")) {
			addDescription(rowData);
		}
		else if(structure.equals("UNITSOFMEASURE")) {
			addUnitsOfMeasure(rowData);
		}
		else if(structure.equals("SALESDATA")) {
			addSalesData(rowData);
		}
		else if(structure.equals("TAXCLASSIFICATIONS")) {
			addTaxClassifications(rowData);
		}
		else if(structure.equals("PLANTDATA")) {
			addPlantData(rowData);
		}
		else if(structure.equals("VALUATIONDATA")) {
			addValuationData(rowData);
		}
		else if(structure.equals("STORAGELOCATIONDATA")) {
			addStorageLocationData(rowData);
		}
		else if(structure.equals("FORECASTPARAMETERS")) {
			addForecastParametersData(rowData);
		}
	}
	
	private void addHeadData(Map<String,FieldValueDTO> rowData) {
		materialId=rowData.get("MATERIAL").getValue();
		industrySector=rowData.get("IND_SECTOR").getValue();
		type=rowData.get("MATL_TYPE").getValue();
	}
	
	private void addClientData(Map<String,FieldValueDTO> rowData) {
		group=rowData.get("MATL_GROUP").getValue();
		oldMaterialNumber=rowData.get("OLD_MAT_NO").getValue();
		baseUom=rowData.get("BASE_UOM").getValue();
		generalItemCategoryGroup=rowData.get("ITEM_CAT").getValue();
		crossPlantMaterialStatus=rowData.get("PUR_STATUS").getValue();
		netWeight=rowData.get("NET_WEIGHT").getDoubleValue();
		weightUnit=rowData.get("UNIT_OF_WT").getValue();
		storageConditions=rowData.get("STOR_CONDS").getValue();
		purchasingValueKey=rowData.get("PUR_VALKEY").getValue();
		transportGroup=rowData.get("TRANS_GRP").getValue();
		labelType=rowData.get("LABEL_TYPE").getValue();
		labelForm=rowData.get("LABEL_FORM").getValue();
		
	}
	
	private void addDescription(Map<String,FieldValueDTO> rowData) {
		if(descriptionList==null) {
			descriptionList=new ArrayList<Description>();
		}
		Description desc=new Description(rowData.get("LANGU").getValue(),rowData.get("MATL_DESC").getValue());
		descriptionList.add(desc);
		
	}
	
	private void addUnitsOfMeasure(Map<String,FieldValueDTO> rowData) {
		
		length=rowData.get("LENGTH").getDoubleValue();
		width=rowData.get("WIDTH").getDoubleValue();
		height=rowData.get("HEIGHT").getDoubleValue();
		grossWeight=rowData.get("GROSS_WT").getDoubleValue();
		
		dimensionUnit=rowData.get("UNIT_DIM").getValue();
		volumeUnit=rowData.get("VOLUMEUNIT").getValue();
		weightUnit=rowData.get("UNIT_OF_WT").getValue();
	}
	
	private void addSalesData(Map<String,FieldValueDTO> rowData) {
		if(salesDataList==null) {
			salesDataList=new ArrayList<SalesData>();
		}
		SalesData salesData=new SalesData();
		salesData.setSalesOrg(rowData.get("SALES_ORG").getValue());
		salesData.setDistributionChannel(rowData.get("DISTR_CHAN").getValue());
		salesData.setStatisticGroup(rowData.get("MATL_STATS").getValue());
		salesData.setAccountAssignment(rowData.get("ACCT_ASSGT").getValue());
		salesData.setDeliveringPlant(rowData.get("DELYG_PLNT").getValue());
		salesDataList.add(salesData);
	}
	
	private void addTaxClassifications(Map<String,FieldValueDTO> rowData) {
		this.departureCountry=rowData.get("DEPCOUNTRY").getValue();
		this.taxType1=rowData.get("TAX_TYPE_1").getValue();
		this.taxClass1=rowData.get("TAXCLASS_1").getValue();
	}
	
	private void addPlantData(Map<String,FieldValueDTO> rowData) {
		if(plantDataList==null) {
			plantDataList=new ArrayList<PlantData>();
		}
		PlantData plantData=new PlantData();
		plantData.setPlant(rowData.get("PLANT").getValue());
		plantData.setProfitCenter(rowData.get("PROFIT_CTR").getValue());
		plantData.setPurchasingGroup(rowData.get("PUR_GROUP").getValue());
		plantData.setGrProcessingTime(rowData.get("GR_PR_TIME").getIntValue());
		plantData.setMrpType(rowData.get("MRP_TYPE").getValue());
		plantData.setReOrderPoint(rowData.get("REORDER_PT").getIntValue());
		plantData.setMrpController(rowData.get("MRP_CTRLER").getValue());
		plantData.setLotSizingProcedure(rowData.get("LOTSIZEKEY").getValue());
		plantData.setMinLotSize(rowData.get("MINLOTSIZE").getIntValue());
		plantData.setProcurementType(rowData.get("PROC_TYPE").getValue());
		plantData.setSpecialProcurement(rowData.get("SPPROCTYPE").getValue());
		plantData.setIssueStorageLocation(rowData.get("ISS_ST_LOC").getValue());
		plantData.setStorageLocationForEP(rowData.get("SLOC_EXPRC").getValue());
		plantData.setPlannedDeliveryTime(rowData.get("PLND_DELRY").getIntValue());
		plantData.setPeriodIndicator(rowData.get("PERIOD_IND").getValue());
		plantData.setAvailabilityCheck(rowData.get("AVAILCHECK").getValue());
		plantData.setIndividualAndCollectiveReq(rowData.get("DEP_REQ_ID").getValue());
		plantData.setLoadingGroup(rowData.get("LOADINGGRP").getValue());
		plantData.setDoNotCost(rowData.get("NO_COSTING").getValue());
		plantData.setAutoReset(rowData.get("AUTO_RESET").getValue());
		plantData.setCountryOfOrigin(rowData.get("COUNTRYORI").getValue());
		plantData.setCommodityCode(rowData.get("COMM_CODE").getValue());
		plantDataList.add(plantData);
	}
	
	private void addValuationData(Map<String,FieldValueDTO> rowData) {
		if(valuationDataList==null) {
			valuationDataList=new ArrayList<ValuationData>();
		}
		ValuationData valuationData=new ValuationData();
		valuationData.setValuationArea(rowData.get("VAL_AREA").getValue());
		valuationData.setValuationClass(rowData.get("VAL_CLASS").getValue());
		valuationData.setPriceControl(rowData.get("PRICE_CTRL").getValue());
		valuationData.setMovingAveragePrice(rowData.get("MOVING_PR").getDoubleValue());
		valuationData.setStandardPrice(rowData.get("STD_PRICE").getDoubleValue());
		valuationData.setPriceUnit(rowData.get("PRICE_UNIT").getIntValue());
		valuationData.setCostWithQtyStructure(rowData.get("QTY_STRUCT").getValue());
		valuationData.setMaterialRelatedOrigin(rowData.get("ORIG_MAT").getValue());
		
		valuationDataList.add(valuationData);
	}
	
	private void addStorageLocationData(Map<String,FieldValueDTO> rowData) {
		if(storageLocationDataList==null) {
			storageLocationDataList=new ArrayList<StorageLocationData>();
		}
		StorageLocationData slocData=new StorageLocationData();
		slocData.setPlant(rowData.get("PLANT").getValue());
		slocData.setStorageLocation(rowData.get("STGE_LOC").getValue());
		storageLocationDataList.add(slocData);
	}
	
	private void addForecastParametersData(Map<String,FieldValueDTO> rowData) {
		if(forecastParametersList==null) {
			forecastParametersList=new ArrayList<ForecastParameters>();
		}
		ForecastParameters fp=new ForecastParameters();
		fp.setPlant(rowData.get("PLANT").getValue());
		fp.setForecastModel(rowData.get("FORE_MODEL").getValue());
		fp.setHistoricalPeriods(rowData.get("HIST_VALS").getIntValue());
		fp.setForecastPeriods(rowData.get("FORE_PDS").getIntValue());
		fp.setInitialize(rowData.get("INITIALIZE").getValue());
		fp.setTrackingLimit(rowData.get("TRACKLIMIT").getDoubleValue());
		fp.setModelSelectionProc(rowData.get("MODEL_SP").getValue());
		forecastParametersList.add(fp);
	}

}
