package org.rfc.material;

import java.io.Serializable;

public class ForecastParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String plant;
	private String forecastModel;
	private int historicalPeriods;
	private int forecastPeriods;
	private String initialize;
	private double trackingLimit;
	private String modelSelectionProc;
	
	public ForecastParameters() {
		super();
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getForecastModel() {
		return forecastModel;
	}

	public void setForecastModel(String forecastModel) {
		this.forecastModel = forecastModel;
	}

	public int getHistoricalPeriods() {
		return historicalPeriods;
	}

	public void setHistoricalPeriods(int historicalPeriods) {
		this.historicalPeriods = historicalPeriods;
	}

	public int getForecastPeriods() {
		return forecastPeriods;
	}

	public void setForecastPeriods(int forecastPeriods) {
		this.forecastPeriods = forecastPeriods;
	}

	public String getInitialize() {
		return initialize;
	}

	public void setInitialize(String initialize) {
		this.initialize = initialize;
	}

	public double getTrackingLimit() {
		return trackingLimit;
	}

	public void setTrackingLimit(double trackingLimit) {
		this.trackingLimit = trackingLimit;
	}

	public String getModelSelectionProc() {
		return modelSelectionProc;
	}

	public void setModelSelectionProc(String modelSelectionProc) {
		this.modelSelectionProc = modelSelectionProc;
	}

}
