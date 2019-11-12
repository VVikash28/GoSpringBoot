package com.capgemini.go.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.dto.AddressDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.service.AddressService;
import com.capgemini.go.utility.InfoConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Address")

public class AddressController {
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
	@PostMapping("/addAddress")
	public String addAddress(@RequestBody Map<String, Object> requestData) {
		UUID uniqueKey = UUID.randomUUID();

		String retailerId = requestData.get("userId").toString();
		String addressId = uniqueKey.toString().substring(0, 3);
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
			result = addressService.addAddress(newAddress);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.Address_Added_Success);
			} else {
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.ADDRESS_ADD_ERROR);
			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}
		return dataResponse.toString();
	}

}
