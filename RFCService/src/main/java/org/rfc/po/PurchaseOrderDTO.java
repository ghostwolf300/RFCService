package org.rfc.po;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class PurchaseOrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String companyCode;
	private String documentType;
	private String purchasingOrg;
	private String purchasingGroup;
	private Date documentDate;
	private String vendor;
	private String supplierPlant;
	private String ourReference;
	private String yourReference;
	private List<POItemDTO> lineItems=null;
	private String metaData;
	private boolean test=true;
	private Date createdDate;
	private String createdBy;
	private String status;
	
	public PurchaseOrderDTO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getDocumentType() {
		return documentType;
	}

	public String getPurchasingOrg() {
		return purchasingOrg;
	}

	public void setPurchasingOrg(String purchasingOrg) {
		this.purchasingOrg = purchasingOrg;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getPurchasingGroup() {
		return purchasingGroup;
	}

	public void setPurchasingGroup(String purchasingGroup) {
		this.purchasingGroup = purchasingGroup;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getSupplierPlant() {
		return supplierPlant;
	}

	public void setSupplierPlant(String supplierPlant) {
		this.supplierPlant = supplierPlant;
	}

	public String getOurReference() {
		return ourReference;
	}

	public void setOurReference(String ourReference) {
		this.ourReference = ourReference;
	}

	public String getYourReference() {
		return yourReference;
	}

	public void setYourReference(String yourReference) {
		this.yourReference = yourReference;
	}

	public List<POItemDTO> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<POItemDTO> lineItems) {
		this.lineItems = lineItems;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
