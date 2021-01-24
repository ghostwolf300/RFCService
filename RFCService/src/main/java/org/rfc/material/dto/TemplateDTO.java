package org.rfc.material.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.material.Template;
import org.rfc.material.TemplateValue;

public class TemplateDTO implements Serializable {

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

	public TemplateDTO() {
		super();
	}
	
	public TemplateDTO(Template template) {
		super();
		this.id=template.getId();
		this.name=template.getName();
		createFieldValues(template.getFieldValues());
	}
	
	private void createFieldValues(Set<TemplateValue> templateValues) {
		Map<String,Set<TemplateValue>> valueMap=new HashMap<String,Set<TemplateValue>>();
		for(TemplateValue tv : templateValues) {
			String struct=tv.getKey().getBapiStructure();
			if(!valueMap.containsKey(struct)) {
				valueMap.put(struct,new HashSet<TemplateValue>());
			}
			valueMap.get(struct).add(tv);
		}
		this.headData=createStructure("HEADDATA",valueMap.get("HEADDATA"));
		this.clientData=createStructure("CLIENTDATA",valueMap.get("CLIENTDATA"));
		this.unitsOfMeasure=createStructure("UNITSOFMEASURE",valueMap.get("UNITSOFMEASURE"));
		this.taxClassifications=createStructure("TAXCLASSIFICATIONS",valueMap.get("TAXCLASSIFICATIONS"));
		this.materialDescription=createTable("MATERIALDESCRIPTION",valueMap.get("MATERIALDESCRIPTION"));
		this.salesData=createTable("SALESDATA",valueMap.get("SALESDATA"));
		this.plantData=createTable("PLANTDATA",valueMap.get("PLANTDATA"));
		this.valuationData=createTable("VALUATIONDATA",valueMap.get("VALUATIONDATA"));
		this.storageLocationData=createTable("STORAGELOCATIONDATA",valueMap.get("STORAGELOCATIONDATA"));
		this.forecastParameters=createTable("FORECASTPARAMETERS",valueMap.get("FORECASTPARAMETERS"));
	}
	
	private BAPIStructureDTO createStructure(String name,Set<TemplateValue> values) {
		List<FieldValueDTO> fields=new ArrayList<FieldValueDTO>();
		for(TemplateValue tv : values) {
			fields.add(new FieldValueDTO(tv));
		}
		BAPIStructureDTO structure=new BAPIStructureDTO(name, fields);
		return structure;
	}
	
	private BAPITableDTO createTable(String name,Set<TemplateValue> values) {
		Map<Integer,List<FieldValueDTO>> rowMap=new HashMap<Integer,List<FieldValueDTO>>();
		for(TemplateValue tv : values) {
			if(!rowMap.containsKey(tv.getKey().getRowId())) {
				rowMap.put(tv.getKey().getRowId(), new ArrayList<FieldValueDTO>());
			}
			rowMap.get(tv.getKey().getRowId()).add(new FieldValueDTO(tv));
		}
		List<FieldValueDTO>[] rows=new ArrayList[rowMap.keySet().size()];
		for(int i=0;i<rows.length;i++) {
			rows[i]=rowMap.get(i);
		}
		BAPITableDTO table=new BAPITableDTO(name,rows);
		return table;
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
