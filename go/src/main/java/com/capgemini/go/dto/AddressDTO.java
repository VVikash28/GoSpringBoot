package com.capgemini.go.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class AddressDTO {
	// attributes
	@Id
	@Column(name = "ADDRESS_ID", unique = true, nullable = false)
	private String addressId;

	@Column(name = "USER_ID", unique = false, nullable = false)
	private String retailerId;

	@Column(name = "BUILDING_NO", unique = false, nullable = false)
	private String buildingNo;

	@Column(name = "CITY", unique = false, nullable = false)
	private String city;

	@Column(name = "STATE", unique = false, nullable = false)
	private String state;

	@Column(name = "COUNTRY", unique = false, nullable = false)
	private String country;

	@Column(name = "ZIP", unique = false, nullable = false)
	private String zip;

	@Column(name = "BASE_ADDRESS_STATUS", unique = false, nullable = false)
	private boolean baseAddressStatus;

	// getters method
	public String getAddressId() {
		return addressId;
	}

	public String getRetailerId() {
		return retailerId;
	}

	
	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getZip() {
		return zip;
	}

	public boolean getBaseaddressstatus() {
		return baseAddressStatus;
	}

//setters method
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

	

	

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public boolean setBaseaddressstatus(boolean baseAddressStatus) {
		return this.baseAddressStatus = baseAddressStatus;
	}

	public AddressDTO() {

	}

	public AddressDTO(String addressId, String retailerId, String buildingNo, String city, String state, String country,
			String zip, boolean baseAddressStatus) {
		this.addressId = addressId;
		this.retailerId = retailerId;
		this.buildingNo = buildingNo;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
		this.baseAddressStatus = baseAddressStatus;

	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	@Override
	public String toString() {
		String result = ("{    AddressId: " + addressId + "   User_Id: " + retailerId + "   Flat and Area:" + buildingNo
				+ "   City:" + city + "    State:" + state + "   Country:" + country + "   Zip Code:" + zip
				+ "    Address Status" + "}");
		return result;

	}
}