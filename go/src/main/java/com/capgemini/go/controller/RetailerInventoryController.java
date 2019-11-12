package com.capgemini.go.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.exception.RetailerInventoryException;
import com.capgemini.go.service.RetailerInventoryService;
import com.capgemini.go.utility.GoLog;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/RetailerInventory")
public class RetailerInventoryController {
	@Autowired
	private RetailerInventoryService retailerInventoryService;

	public RetailerInventoryService getRetailerInventoryService() {
		return retailerInventoryService;
	}

	public void setRetailerInventoryService(RetailerInventoryService retailerInventoryService) {
		this.retailerInventoryService = retailerInventoryService;
	}
		
	@ResponseBody
	@PostMapping("/ShelfTimeReport")
	public String getShelfTimeReport (@RequestBody Map<String, Object> requestData) {
		GoLog.getLogger(RetailerInventoryController.class).info("getShelfTimeReport - " + "Request for Shelf Time Report Received");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		String retailerId = requestData.get("retailerId").toString();
		int reportType = Integer.valueOf(requestData.get("reportType").toString());
		Calendar dateSelection = Calendar.getInstance();
		List<RetailerInventoryBean> result = null;
		switch (reportType) {
			case 1: {
				try {
					result = this.retailerInventoryService.getMonthlyShelfTimeReport(retailerId, dateSelection);
				} catch (RetailerInventoryException error) {
					GoLog.getLogger(RetailerInventoryController.class).error("getShelfTimeReport - " + error.getMessage());
					((ObjectNode) dataResponse).put("Error :", error.getMessage());
					return dataResponse.toString();
				}
				break;
			}
			case 2: {
				try {
					result = this.retailerInventoryService.getQuarterlyShelfTimeReport(retailerId, dateSelection);
				} catch (RetailerInventoryException error) {
					GoLog.getLogger(RetailerInventoryController.class).error("getShelfTimeReport - " + error.getMessage());
					((ObjectNode) dataResponse).put("Error :", error.getMessage());
					return dataResponse.toString();
				}
				break;
			}
			case 3: {
				try {
					result = this.retailerInventoryService.getYearlyShelfTimeReport(retailerId, dateSelection);
				} catch (RetailerInventoryException error) {
					GoLog.getLogger(RetailerInventoryController.class).error("getShelfTimeReport - " + error.getMessage());
					((ObjectNode) dataResponse).put("Error :", error.getMessage());
					return dataResponse.toString();
				}
				break;
			}
			default: {
				GoLog.getLogger(RetailerInventoryController.class).error("getShelfTimeReport - " + "Invalid Argument Received");
				((ObjectNode) dataResponse).put("Error :", "Invalid Argument Received");
				return dataResponse.toString();
			}
		}
		if (result == null) {
			GoLog.getLogger(RetailerInventoryController.class).error("getShelfTimeReport - " + "Data could not be obtained from database");
			((ObjectNode) dataResponse).put("Error :", "Data could not be obtained from database");
			return dataResponse.toString();
		}
		JsonArray itemList = new JsonArray();
		for (RetailerInventoryBean item : result) {
			JsonObject itemObj = new JsonObject();
			itemObj.addProperty ("retailerId", item.getRetailerId());
			itemObj.addProperty("retailerName", item.getRetailerName());
			itemObj.addProperty("productCategoryNumber", item.getProductCategoryNumber());
			itemObj.addProperty("productCategoryName", item.getProductCategoryName());
			itemObj.addProperty("productUniqueId", item.getProductUniqueId());
			itemObj.addProperty("shelfTimePeriod", RetailerInventoryBean.periodToString(item.getShelfTimePeriod()));
			itemList.add(itemObj);
		}
		GoLog.getLogger(RetailerInventoryController.class).info("getShelfTimeReport - " + "Sent requested data");
		return itemList.toString();
	}
	
	@ResponseBody
	@PostMapping("/DeliveryTimeReport")
	public String getDeliveryTimeReport (@RequestBody Map<String, Object> requestData) {
		GoLog.getLogger(RetailerInventoryController.class).info("getDeliveryTimeReport - " + "Request for Delivery Time Report Received");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		String retailerId = requestData.get("retailerId").toString();
		int reportType = Integer.valueOf(requestData.get("reportType").toString());
		List<RetailerInventoryBean> result = null;
		switch (reportType) {
			case 1: {
				try {
					result = this.retailerInventoryService.getItemWiseDeliveryTimeReport(retailerId);
				} catch (RetailerInventoryException error) {
					GoLog.getLogger(RetailerInventoryController.class).error("getDeliveryTimeReport - " + error.getMessage());
					((ObjectNode) dataResponse).put("Error :", error.getMessage());
					return dataResponse.toString();
				}
				break;
			}
			case 2: {
				try {
					result = this.retailerInventoryService.getCategoryWiseDeliveryTimeReport(retailerId);
				} catch (RetailerInventoryException error) {
					GoLog.getLogger(RetailerInventoryController.class).error("getDeliveryTimeReport - " + error.getMessage());
					((ObjectNode) dataResponse).put("Error :", error.getMessage());
					return dataResponse.toString();
				}
				break;
			}
			case 3: {
				try {
					result = this.retailerInventoryService.getOutlierCategoryItemWiseDeliveryTimeReport(retailerId);
				} catch (RetailerInventoryException error) {
					GoLog.getLogger(RetailerInventoryController.class).error("getDeliveryTimeReport - " + error.getMessage());
					((ObjectNode) dataResponse).put("Error :", error.getMessage());
					return dataResponse.toString();
				}
				break;
			}
			default: {
				GoLog.getLogger(RetailerInventoryController.class).error("getDeliveryTimeReport - " + "Invalid Argument Received");
				((ObjectNode) dataResponse).put("Error :", "Invalid Argument Received");
				return dataResponse.toString();
			}
		}
		if (result == null) {
			GoLog.getLogger(RetailerInventoryController.class).error("getDeliveryTimeReport - " + "Data could not be obtained from database");
			((ObjectNode) dataResponse).put("Error :", "Data could not be obtained from database");
			return dataResponse.toString();
		}
		JsonArray itemList = new JsonArray();
		for (RetailerInventoryBean item : result) {
			JsonObject itemObj = new JsonObject();
			itemObj.addProperty ("retailerId", item.getRetailerId());
			itemObj.addProperty("retailerName", item.getRetailerName());
			itemObj.addProperty("productCategoryNumber", item.getProductCategoryNumber());
			itemObj.addProperty("productCategoryName", item.getProductCategoryName());
			itemObj.addProperty("productUniqueId", item.getProductUniqueId());
			itemObj.addProperty("deliveryTimePeriod", RetailerInventoryBean.periodToString(item.getDeliveryTimePeriod()));
			itemList.add(itemObj);
		}
		GoLog.getLogger(RetailerInventoryController.class).info("getDeliveryTimeReport - " + "Sent requested data");
		return itemList.toString();
	}
	
	@ResponseBody
	@PostMapping("/RetailerList")
	public String getRetailerList () {
		GoLog.getLogger(RetailerInventoryController.class).info("getRetailerList - " + "Request for Retailer List Received");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		JsonArray retailerList = new JsonArray();
		try {
			List<RetailerInventoryBean> result = this.retailerInventoryService.getListOfRetailers();
			for (RetailerInventoryBean item : result) {
				JsonObject retailerObj = new JsonObject();
				retailerObj.addProperty ("retailerId", item.getRetailerId());
				retailerObj.addProperty("retailerName", item.getRetailerName());
				retailerList.add(retailerObj);
			}
		} catch (Exception error) {
			GoLog.getLogger(RetailerInventoryController.class).error("getRetailerList - " + error.getMessage());
			((ObjectNode) dataResponse).put("Error :", error.getMessage());
			return dataResponse.toString();
		}
		GoLog.getLogger(RetailerInventoryController.class).info("getRetailerList - " + "Sent requested data");
		return retailerList.toString();
	}
	
	@ResponseBody
	@GetMapping("/RetailerInventoryById/{retailerId}")
	public String getRetailerInventoryById (@PathVariable String retailerId) {
		GoLog.getLogger(RetailerInventoryController.class).info("getRetailerInventoryById - " + "Request for " + retailerId + " Inventory Received");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		JsonArray itemList = new JsonArray();
		try {
			List<RetailerInventoryBean> result = this.retailerInventoryService.getInventoryById(retailerId);
			for (RetailerInventoryBean item : result) {
				JsonObject itemObj = new JsonObject();
				itemObj.addProperty ("retailerId", item.getRetailerId());
				itemObj.addProperty("retailerName", item.getRetailerName());
				itemObj.addProperty("productCategoryNumber", item.getProductCategoryNumber());
				itemObj.addProperty("productCategoryName", item.getProductCategoryName());
				itemObj.addProperty("productName", item.getProductName());
				itemObj.addProperty("productUniqueId", item.getProductUniqueId());
				itemList.add(itemObj);
			}
		} catch (Exception error) {
			GoLog.getLogger(RetailerInventoryController.class).error("getRetailerInventoryById - " + error.getMessage());
			((ObjectNode) dataResponse).put("Error :", error.getMessage());
			return dataResponse.toString();
		}
		GoLog.getLogger(RetailerInventoryController.class).info("getRetailerInventoryById - " + "Sent requested data");
		return itemList.toString();
	}
	
	@ResponseBody
	@PostMapping("/UpdateReceiveTime")
	public String updateReceiveTime (@RequestBody Map<String, Object> requestData) {
		String retailerId = requestData.get("retailerId").toString();
		String productUin = requestData.get("productUin").toString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		try {
			this.retailerInventoryService.updateItemReceiveTimestamp(retailerId, productUin);
		} catch (RetailerInventoryException error) {
			GoLog.getLogger(RetailerInventoryController.class).info("updateReceiveTime - " + error.getMessage());
			((ObjectNode) dataResponse).put("Error :", error.getMessage());
			return dataResponse.toString();
		}
		((ObjectNode) dataResponse).put("Message :", "Item Receive Time Stamp updated successfully!");
		return dataResponse.toString();
	}
	
	@ResponseBody
	@PostMapping("/UpdateSaleTime")
	public String updateSaleTime (@RequestBody Map<String, Object> requestData) {
		String retailerId = requestData.get("retailerId").toString();
		String productUin = requestData.get("productUin").toString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		try {
			this.retailerInventoryService.updateItemSaleTimestamp(retailerId, productUin);
		} catch (RetailerInventoryException error) {
			GoLog.getLogger(RetailerInventoryController.class).info("updateSaleTime - " + error.getMessage());
			((ObjectNode) dataResponse).put("Error :", error.getMessage());
			return dataResponse.toString();
		}
		((ObjectNode) dataResponse).put("Message :", "Item Sale Time Stamp updated successfully!");
		return dataResponse.toString();
	}
}
