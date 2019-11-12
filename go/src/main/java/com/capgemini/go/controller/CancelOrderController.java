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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;;

@RestController
public class CancelOrderController {

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
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	@CrossOrigin(origins = "*")

	public String cancelOrder(@RequestBody Map<String, Object> model) throws Exception {
		String result = null;
		String userID = model.get("userId").toString();
		String orderID = model.get("orderId").toString();
		try {
			String message = salesRepresentativeService.cancelOrder(orderID, userID);
			JsonArray cancelOrderList = new JsonArray();
			JsonObject cancelOrderObj = new JsonObject();
			cancelOrderObj.addProperty("Success", message);
			cancelOrderList.add(cancelOrderObj);

			result = cancelOrderList.toString();
		} catch (Exception e) {
			JsonArray cancelOrderList = new JsonArray();
			JsonObject cancelOrderObj = new JsonObject();
			cancelOrderObj.addProperty("Error :", e.getMessage());
			cancelOrderList.add(cancelOrderObj);

			result = cancelOrderList.toString();
		}
		return result;
	}
}