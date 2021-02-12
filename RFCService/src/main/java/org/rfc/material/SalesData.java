package org.rfc.material;

import java.io.Serializable;

public class SalesData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String salesOrg;
	private String distributionChannel;
	private String statisticGroup;
	private String itemCategoryGroup;
	private String accountAssignment;
	private String deliveringPlant;
	
	public SalesData() {
		super();
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getStatisticGroup() {
		return statisticGroup;
	}

	public void setStatisticGroup(String statisticGroup) {
		this.statisticGroup = statisticGroup;
	}

	public String getItemCategoryGroup() {
		return itemCategoryGroup;
	}

	public void setItemCategoryGroup(String itemCategoryGroup) {
		this.itemCategoryGroup = itemCategoryGroup;
	}

	public String getAccountAssignment() {
		return accountAssignment;
	}

	public void setAccountAssignment(String accountAssignment) {
		this.accountAssignment = accountAssignment;
	}

	public String getDeliveringPlant() {
		return deliveringPlant;
	}

	public void setDeliveringPlant(String deliveringPlant) {
		this.deliveringPlant = deliveringPlant;
	}

}
