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
import com.google.gson.JsonObject;

@RestController
public class CancelProductController {

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
	@RequestMapping(value = "/cancelProduct", method = RequestMethod.POST)
	@CrossOrigin(origins = "*")
	// public String cancelProduct(@RequestParam("userId") String userId,
	// @RequestParam("orderId") String orderId,
	// @RequestParam("productId") String productId, @RequestParam("quantity") int
	// quantity) throws Exception {
	public String cancelProduct(@RequestBody Map<String, Object> model) throws Exception {
		String result = null;
		String userID = model.get("userId").toString();
		String orderID = model.get("orderId").toString();
		String productID = model.get("productId").toString();
		int quantityToCancel = Integer.parseInt(model.get("quantity").toString());
		try {
			String message = salesRepresentativeService.cancelProduct(orderID, userID, productID, quantityToCancel);
			JsonArray cancelProductList = new JsonArray();
			JsonObject cancelProductObj = new JsonObject();
			cancelProductObj.addProperty("Success", message);
			cancelProductList.add(cancelProductObj);

			result = cancelProductList.toString();
		} catch (Exception e) {
			JsonArray cancelProductList = new JsonArray();
			JsonObject cancelProductObj = new JsonObject();
			cancelProductObj.addProperty("Error :", e.getMessage());
			cancelProductList.add(cancelProductObj);

			result = cancelProductList.toString();
		}
		return result;
	}
}
