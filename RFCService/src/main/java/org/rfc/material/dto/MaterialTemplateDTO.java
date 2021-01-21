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
	private BAPIStructureDTO headData;
	private BAPIStructureDTO clientData;
	private BAPIStructureDTO  unitsOfMeasure;
	private BAPIStructureDTO  taxClassifications;
	private BAPITableDTO  materialDescription;
	private BAPITableDTO salesData;
	private BAPITableDTO plantData;
	private BAPITableDTO valuationData;
	private BAPITableDTO storageLocationData;
	private BAPITableDTO forecastParameters;	

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

	public BAPIStructureDTO getHeadData() {
		return headData;
	}

	public void setHeadData(BAPIStructureDTO headData) {
		this.headData = headData;
	}

	public BAPIStructureDTO getClientData() {
		return clientData;
	}

	public void setClientData(BAPIStructureDTO clientData) {
		this.clientData = clientData;
	}

	public BAPIStructureDTO getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	public void setUnitsOfMeasure(BAPIStructureDTO unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}

	public BAPIStructureDTO getTaxClassifications() {
		return taxClassifications;
	}

	public void setTaxClassifications(BAPIStructureDTO taxClassifications) {
		this.taxClassifications = taxClassifications;
	}

	public BAPITableDTO getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(BAPITableDTO materialDescription) {
		this.materialDescription = materialDescription;
	}

	public BAPITableDTO getSalesData() {
		return salesData;
	}

	public void setSalesData(BAPITableDTO salesData) {
		this.salesData = salesData;
	}

	public BAPITableDTO getPlantData() {
		return plantData;
	}

	public void setPlantData(BAPITableDTO plantData) {
		this.plantData = plantData;
	}

	public BAPITableDTO getValuationData() {
		return valuationData;
	}

	public void setValuationData(BAPITableDTO valuationData) {
		this.valuationData = valuationData;
	}

	public BAPITableDTO getStorageLocationData() {
		return storageLocationData;
	}

	public void setStorageLocationData(BAPITableDTO storageLocationData) {
		this.storageLocationData = storageLocationData;
	}

	public BAPITableDTO getForecastParameters() {
		return forecastParameters;
	}

	public void setForecastParameters(BAPITableDTO forecastParameters) {
		this.forecastParameters = forecastParameters;
	}

}
