package org.rfc.material.dto;

import java.io.Serializable;
import java.util.List;

public class MaterialTemplateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private HeadDataDTO headData;
	private ClientDataDTO clientData;
	private UnitsOfMeasureDTO unitsOfMeasure;
	private TaxClassificationsDTO taxClassifications;
	private List<MaterialDescriptionDTO> materialDescriptionList;
	private List<SalesDataDTO> salesDataList;
	private List<PlantDataDTO> plantDataList;
	private List<ValuationDataDTO> valuationDataList;
	private List<StorageLocationDataDTO> storageLocationDataList;
	private List<ForecastParametersDTO> forecastParametersList;	

	public MaterialTemplateDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HeadDataDTO getHeadData() {
		return headData;
	}

	public void setHeadData(HeadDataDTO headData) {
		this.headData = headData;
	}

	public ClientDataDTO getClientData() {
		return clientData;
	}

	public void setClientData(ClientDataDTO clientData) {
		this.clientData = clientData;
	}

	public UnitsOfMeasureDTO getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	public void setUnitsOfMeasure(UnitsOfMeasureDTO unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}

	public TaxClassificationsDTO getTaxClassifications() {
		return taxClassifications;
	}

	public void setTaxClassifications(TaxClassificationsDTO taxClassifications) {
		this.taxClassifications = taxClassifications;
	}

	public List<MaterialDescriptionDTO> getMaterialDescriptionList() {
		return materialDescriptionList;
	}

	public void setMaterialDescriptionList(List<MaterialDescriptionDTO> materialDescriptionList) {
		this.materialDescriptionList = materialDescriptionList;
	}

	public List<SalesDataDTO> getSalesDataList() {
		return salesDataList;
	}

	public void setSalesDataList(List<SalesDataDTO> salesDataList) {
		this.salesDataList = salesDataList;
	}

	public List<PlantDataDTO> getPlantDataList() {
		return plantDataList;
	}

	public void setPlantDataList(List<PlantDataDTO> plantDataList) {
		this.plantDataList = plantDataList;
	}

	public List<ValuationDataDTO> getValuationDataList() {
		return valuationDataList;
	}

	public void setValuationDataList(List<ValuationDataDTO> valuationDataList) {
		this.valuationDataList = valuationDataList;
	}

	public List<StorageLocationDataDTO> getStorageLocationDataList() {
		return storageLocationDataList;
	}

	public void setStorageLocationDataList(List<StorageLocationDataDTO> storageLocationDataList) {
		this.storageLocationDataList = storageLocationDataList;
	}

	public List<ForecastParametersDTO> getForecastParametersList() {
		return forecastParametersList;
	}

	public void setForecastParametersList(List<ForecastParametersDTO> forecastParametersList) {
		this.forecastParametersList = forecastParametersList;
	}

}
