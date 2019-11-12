package com.capgemini.go.bean;

import java.time.Period;

public class RetailerInventoryBean {
	private String retailerId;
	private String retailerName;
	private byte productCategoryNumber;
	private String productCategoryName;
	private String productName;
	private String productUniqueId;
	private Period deliveryTimePeriod;
	private Period shelfTimePeriod;
	
	// Getters
	public String getRetailerId() {return retailerId;}
	public String getRetailerName() {return retailerName;}
	public byte getProductCategoryNumber() {return productCategoryNumber;}
	public String getProductCategoryName() {return productCategoryName;}
	public String getProductName() {return productName;}
	public String getProductUniqueId() {return productUniqueId;}
	public Period getDeliveryTimePeriod() {return deliveryTimePeriod;}
	public Period getShelfTimePeriod() {return shelfTimePeriod;}
	
	// Setters
	public void setRetailerName(String retailerName) {this.retailerName = retailerName;}
	public void setRetailerId(String retailerId) {this.retailerId = retailerId;}
	public void setProductCategoryNumber(byte productCategoryNumber) {this.productCategoryNumber = productCategoryNumber;}
	public void setProductCategoryName(String productCategoryName) {this.productCategoryName = productCategoryName;}
	public void setProductName(String productName) {this.productName = productName;}
	public void setProductUniqueId(String productUniqueId) {this.productUniqueId = productUniqueId;}
	public void setDeliveryTimePeriod(Period deliveryTimePeriod) {this.deliveryTimePeriod = deliveryTimePeriod;}
	public void setShelfTimePeriod(Period shelfTimePeriod) {this.shelfTimePeriod = shelfTimePeriod;}
	
	// Constructors
	public RetailerInventoryBean () {
		this.retailerId = null;
		this.retailerName = null;
		this.productCategoryNumber = 0;
		this.productCategoryName = null;
		this.productUniqueId = null;
		this.deliveryTimePeriod = null;
		this.shelfTimePeriod = null;
	}
	
	public RetailerInventoryBean(String retailerId, String retailerName, byte productCategoryNumber,
			String productCategoryName, String productName, String productUniqueId, Period deliveryTimePeriod, Period shelfTimePeriod) {
		this.retailerId = retailerId;
		this.retailerName = retailerName;
		this.productCategoryNumber = productCategoryNumber;
		this.productCategoryName = productCategoryName;
		this.productName = productName;
		this.productUniqueId = productUniqueId;
		this.deliveryTimePeriod = deliveryTimePeriod;
		this.shelfTimePeriod = shelfTimePeriod;
	}
	
	public static String periodToString (Period period) {
		return "Years: " + period.getYears() + " Months: " + period.getMonths() + " Days: " + period.getDays();
	}
}
