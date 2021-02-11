package org.rfc.material.dto;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MaterialDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
	
	private String materialId=null;
	private String type=null;
	private String industrySector=null;
	private String oldMaterialNumber=null;
	private String baseUom=null;
	private String group=null;
	private String generalItemCategoryGroup=null;
	private String crossPlantMaterialStatus=null;
	private String weightUnit=null;
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
	private String labelType=null;
	private String labelForm=null;
	private double periodicUnitPrice;
	//-------TAXCLASSIFICATIONS----------------
	private String departureCountry;
	private String taxType1;
	private String taxClass1;
	private List<DescriptionDTO> descriptions;
	private List<SalesDataDTO> salesDataSet;
	private List<PlantDataDTO> plantDataSet;
	private List<ValuationDataDTO> valuationDataSet;
	private List<StorageLocationDataDTO> storageLocationDataSet;
	private List<ForecastParametersDTO> forecastParametersSet;
	
	public MaterialDTO() {
		super();
	}

	public String getMaterialId() {
		return materialId;
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

	public List<DescriptionDTO> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<DescriptionDTO> descriptions) {
		this.descriptions = descriptions;
	}

	public List<SalesDataDTO> getSalesDataSet() {
		return salesDataSet;
	}

	public void setSalesDataSet(List<SalesDataDTO> salesDataSet) {
		this.salesDataSet = salesDataSet;
	}

	public List<PlantDataDTO> getPlantDataSet() {
		return plantDataSet;
	}

	public void setPlantDataSet(List<PlantDataDTO> plantDataSet) {
		this.plantDataSet = plantDataSet;
	}

	public List<ValuationDataDTO> getValuationDataSet() {
		return valuationDataSet;
	}

	public void setValuationDataSet(List<ValuationDataDTO> valuationDataSet) {
		this.valuationDataSet = valuationDataSet;
	}

	public List<StorageLocationDataDTO> getStorageLocationDataSet() {
		return storageLocationDataSet;
	}

	public void setStorageLocationDataSet(List<StorageLocationDataDTO> storageLocationDataSet) {
		this.storageLocationDataSet = storageLocationDataSet;
	}

	public List<ForecastParametersDTO> getForecastParametersSet() {
		return forecastParametersSet;
	}

	public void setForecastParametersSet(List<ForecastParametersDTO> forecastParametersSet) {
		this.forecastParametersSet = forecastParametersSet;
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
		try {
			netWeight=format.parse(rowData.get("NET_WEIGHT").getValue()).doubleValue();
		} 
		catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		weightUnit=rowData.get("UNIT_OF_WT").getValue();
		storageConditions=rowData.get("STOR_CONDS").getValue();
		purchasingValueKey=rowData.get("PUR_VALKEY").getValue();
		transportGroup=rowData.get("TRANS_GRP").getValue();
		labelType=rowData.get("LABEL_TYPE").getValue();
		labelForm=rowData.get("LABEL_FORM").getValue();
		
	}
	
	private void addDescription(Map<String,FieldValueDTO> rowData) {
		if(descriptions==null) {
			descriptions=new ArrayList<DescriptionDTO>();
		}
		DescriptionDTO desc=new DescriptionDTO(rowData.get("LANGU").getValue(),rowData.get("MATL_DESC").getValue());
		descriptions.add(desc);
		
	}
	
	private void addUnitsOfMeasure(Map<String,FieldValueDTO> rowData) {
		try {
			length=format.parse(rowData.get("LENGTH").getValue()).doubleValue();
			width=format.parse(rowData.get("WIDTH").getValue()).doubleValue();
			height=format.parse(rowData.get("HEIGHT").getValue()).doubleValue();
			grossWeight=format.parse(rowData.get("GROSS_WT").getValue()).doubleValue();
		} 
		catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		dimensionUnit=rowData.get("UNIT_DIM").getValue();
		volumeUnit=rowData.get("VOLUMEUNIT").getValue();
		weightUnit=rowData.get("UNIT_OF_WT").getValue();
	}
	

}
