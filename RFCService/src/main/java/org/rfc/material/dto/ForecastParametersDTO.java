package org.rfc.material.dto;

import java.io.Serializable;

public class ForecastParametersDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FieldValueDTO plant;
	private FieldValueDTO foreModel;
	private FieldValueDTO histVals;
	private FieldValueDTO forePds;
	private FieldValueDTO initialize;
	private FieldValueDTO trackLimit;
	private FieldValueDTO modelSp;
	
	public ForecastParametersDTO() {
		super();
	}

	public FieldValueDTO getPlant() {
		return plant;
	}

	public void setPlant(FieldValueDTO plant) {
		this.plant = plant;
	}

	public FieldValueDTO getForeModel() {
		return foreModel;
	}

	public void setForeModel(FieldValueDTO foreModel) {
		this.foreModel = foreModel;
	}

	public FieldValueDTO getHistVals() {
		return histVals;
	}

	public void setHistVals(FieldValueDTO histVals) {
		this.histVals = histVals;
	}

	public FieldValueDTO getForePds() {
		return forePds;
	}

	public void setForePds(FieldValueDTO forePds) {
		this.forePds = forePds;
	}

	public FieldValueDTO getInitialize() {
		return initialize;
	}

	public void setInitialize(FieldValueDTO initialize) {
		this.initialize = initialize;
	}

	public FieldValueDTO getTrackLimit() {
		return trackLimit;
	}

	public void setTrackLimit(FieldValueDTO trackLimit) {
		this.trackLimit = trackLimit;
	}

	public FieldValueDTO getModelSp() {
		return modelSp;
	}

	public void setModelSp(FieldValueDTO modelSp) {
		this.modelSp = modelSp;
	}

}
