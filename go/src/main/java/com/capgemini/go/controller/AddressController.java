package com.capgemini.go.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.dto.AddressDTO;
import com.capgemini.go.exception.AddressException;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.service.AddressService;
import com.capgemini.go.utility.InfoConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Address")

public class AddressController {
	
	private Logger logger = Logger.getRootLogger();

	@Autowired
	private AddressService addressService;

	// Getters and Setters

	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	@ResponseBody
	@PostMapping("/viewAddress")
	public String getAllAddress(@RequestBody Map<String, Object> requestData) throws AddressException {
		String userId = requestData.get("userId").toString();
		List<AddressDTO> addresses = addressService.viewAllAddress(userId);
		JsonArray addressList = new JsonArray();

		for (AddressDTO address : addresses) {
			JsonObject addressObj = new JsonObject();
			addressObj.addProperty("addressId", address.getAddressId());
			addressObj.addProperty("userId", address.getRetailerId());
			addressObj.addProperty("building_number", address.getBuildingNo());
			addressObj.addProperty("city", address.getCity());
			addressObj.addProperty("state", address.getState());
			addressObj.addProperty("country", address.getCountry());
			addressObj.addProperty("zip", address.getZip());

			addressList.add(addressObj);
		}

		return addressList.toString();
	}

	@ResponseBody
	@PostMapping("/addAddress")
	public String addAddress(@RequestBody Map<String, Object> requestData) {
		UUID uniqueKey = UUID.randomUUID();

		String retailerId = requestData.get("userId").toString();
		String addressId = uniqueKey.toString().substring(0, 5) + "2";
		String flat_area = requestData.get("building_number").toString();
		String city = requestData.get("city").toString();
		String state = requestData.get("state").toString();
		String country = requestData.get("country").toString();
		String zip = requestData.get("zip").toString();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			AddressDTO newAddress = new AddressDTO(retailerId + addressId, retailerId, flat_area, city, state, country,
					zip, true);
			System.out.println(newAddress.toString());
			result = addressService.addAddress(newAddress);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.Address_Added_Success);
			} else {
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.ADDRESS_ADD_ERROR);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}
		return dataResponse.toString();
	}

	@ResponseBody
	@PostMapping("/updateAddress")
	public String updateAddress(@RequestBody Map<String, Object> requestData) {

		String addressId = requestData.get("addressId").toString();
		String retailerId = requestData.get("userId").toString();
		String flat_area = requestData.get("building_number").toString();
		String city = requestData.get("city").toString();
		String state = requestData.get("state").toString();
		String country = requestData.get("country").toString();
		String zip = requestData.get("zip").toString();

		AddressDTO updateAddress = new AddressDTO(addressId, retailerId, flat_area, city, state, country, zip, true);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			result = addressService.updateAddress(updateAddress);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.Address_Update_Success);
				logger.info(InfoConstants.Address_Update_Success);
			} else {
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.ADDRESS_UPDATE_ERROR);
				logger.error(ExceptionConstants.ADDRESS_UPDATE_ERROR);
			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}
		return dataResponse.toString();
	}

	@ResponseBody
	@PostMapping("/deleteAddress")
	public String deleteAddress(@RequestBody Map<String, Object> requestData) {

		String addressId = requestData.get("addressId").toString();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			result = addressService.deleteAddress(addressId);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.Address_Delete_Success);
				logger.info(InfoConstants.Address_Delete_Success);
			} else {
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.ADDRESS_DELETE_ERROR);
				logger.error(ExceptionConstants.ADDRESS_DELETE_ERROR);
			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}
		return dataResponse.toString();
	}

}
