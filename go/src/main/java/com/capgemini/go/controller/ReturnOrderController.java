package com.capgemini.go.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.service.SalesRepresentativeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;;

@RestController
public class ReturnOrderController {

	@Autowired
	private SalesRepresentativeService salesRepresentativeService;

	// Getters and Setters

	public SalesRepresentativeService getSalesRepresentativeService() {
		return salesRepresentativeService;
	}

	public void setSalesRepresentativeService(SalesRepresentativeService salesRepresentativeService) {
		this.salesRepresentativeService = salesRepresentativeService;
	}

	@ResponseBody
	@RequestMapping(value = "/returnOrder", method = RequestMethod.POST)
	@CrossOrigin(origins = "*")

	public String returnOrder(@RequestBody Map<String, Object> requestData) throws Exception {
		boolean returnStatus = false;
		String userID = requestData.get("userid").toString();
		String orderID = requestData.get("orderid").toString();
		String reason = requestData.get("reason").toString();
		String result = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		try {
			returnStatus = salesRepresentativeService.returnOrder(orderID, userID, reason);

			if (returnStatus == true)
				result = "Return Order Processed";

			((ObjectNode) dataResponse).put("Success :", result);

		} catch (Exception e) {
			((ObjectNode) dataResponse).put("Success :", result);
			((ObjectNode) dataResponse).put("Error :", "Error in processing return request for order");
		}
		return dataResponse.toString();
	}
}
