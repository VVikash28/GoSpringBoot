package com.capgemini.go.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.ProductException;
import com.capgemini.go.service.ProductService;
import com.capgemini.go.utility.GoLog;
import com.capgemini.go.utility.InfoConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Products")
public class ProductController {

	@Autowired
	private ProductService productService;

	// Getters and Setters

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@ResponseBody
	@GetMapping("/list")
	public String getAllProducts() throws ProductException {

		List<ProductDTO> products = productService.viewAllProducts();
		JsonArray productList = new JsonArray();

		for (ProductDTO prod : products) {
			JsonObject productObj = new JsonObject();
			productObj.addProperty("prodid", prod.getProductId());
			productObj.addProperty("prodName", prod.getProductName());
			productObj.addProperty("prodBrand", prod.getManufacturer());
			productObj.addProperty("prodSpec", prod.getSpecification());
			productObj.addProperty("prodDim", prod.getDimension());
			productObj.addProperty("prodQty", prod.getQuantity());
			productObj.addProperty("prodPrice", prod.getPrice());
			productObj.addProperty("prodColor", prod.getColour());
			productObj.addProperty("category", prod.getProductCategory());
			productObj.addProperty("isActive", true);
			productObj.addProperty("photoPath", "assets/images/products/" + prod.getProductId() + ".jpg");
			productList.add(productObj);
		}

		return productList.toString();
	}

	@ResponseBody
	@PostMapping("/addProduct")
	public String addProduct(@RequestBody Map<String, Object> requestData) {

		String productId = requestData.get("prodid").toString();
		String productName = requestData.get("prodName").toString();
		double price = Double.parseDouble(requestData.get("prodPrice").toString());
		String colour = requestData.get("prodColor").toString();
		String dimension = requestData.get("prodDim").toString();
		String specification = requestData.get("prodSpec").toString();
		String manufacturer = requestData.get("prodBrand").toString();
		/*********************
		 * Quantity Can only be 1,2,3,4 or 5 1 = CAMPING 2 = GOLF 3 = MOUNTAINEERING 4 =
		 * OUTDOOR 5 = PERSONAL
		 *************************/
		int quantity = Integer.parseInt(requestData.get("prodQty").toString());
		int productCategory = Integer.parseInt(requestData.get("category").toString());

		ProductDTO newProd = new ProductDTO(productId, price, colour, dimension, specification, manufacturer, quantity,
				productCategory, productName);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			result = productService.addProduct(newProd);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.Product_Added_Success);
			} else {
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.PRODUCT_ADD_ERROR);
			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}

		return dataResponse.toString();

	}
	
	@ResponseBody
	@PostMapping("/deleteProduct")
	public String deleteProduct(@RequestBody Map<String, Object> requestData) {

		String productId = requestData.get("prodid").toString();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			result = productService.deleteProduct(productId);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.Product_Delete_Success);
				GoLog.getLogger(ProductController.class).info(InfoConstants.Product_Delete_Success);
			} else {
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.PRODUCT_DELETE_ERROR);
				GoLog.getLogger(ProductController.class).error(ExceptionConstants.PRODUCT_DELETE_ERROR);
			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}

		return dataResponse.toString();

	}
	
	@ResponseBody
	@PostMapping("/editProduct")
	public String editProduct(@RequestBody Map<String, Object> requestData) {

		String productId = requestData.get("prodid").toString();
		String productName = requestData.get("prodName").toString();
		double price = Double.parseDouble(requestData.get("prodPrice").toString());
		String colour = requestData.get("prodColor").toString();
		String dimension = requestData.get("prodDim").toString();
		String specification = requestData.get("prodSpec").toString();
		String manufacturer = requestData.get("prodBrand").toString();
		/*********************
		 * Quantity Can only be 1,2,3,4 or 5 1 = CAMPING 2 = GOLF 3 = MOUNTAINEERING 4 =
		 * OUTDOOR 5 = PERSONAL
		 *************************/
		int quantity = Integer.parseInt(requestData.get("prodQty").toString());
		int productCategory = Integer.parseInt(requestData.get("category").toString());

		ProductDTO newProd = new ProductDTO(productId, price, colour, dimension, specification, manufacturer, quantity,
				productCategory, productName);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			result = productService.editProduct(newProd);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.Product_Update_Success);
				GoLog.getLogger(ProductController.class).info(InfoConstants.Product_Update_Success);
			} else {
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.PRODUCT_UPDATE_ERROR);
				GoLog.getLogger(ProductController.class).error(ExceptionConstants.PRODUCT_UPDATE_ERROR);
			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}

		return dataResponse.toString();

	}

}
