package com.capgemini.go.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.dto.WhishlistIdDTO;
import com.capgemini.go.dto.WishlistDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.WishlistException;
import com.capgemini.go.service.WishlistService;
import com.capgemini.go.utility.InfoConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Wishlist")

public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	// Getters and Setters

	public WishlistService getWishlistService() {
		return wishlistService;
	}

	public void setWishlistService(WishlistService wishlistService) {
		this.wishlistService = wishlistService;
		
	}

	@ResponseBody
	@PostMapping("/AddtoWishlist")
	public String addProductToWishlist(@RequestBody Map<String, Object> requestData) {

		String productId = requestData.get("prodid").toString();
		String userId = requestData.get("userid").toString();

		WhishlistIdDTO wishId = new WhishlistIdDTO(userId, productId);
		WishlistDTO newWish = new WishlistDTO(wishId);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			result = wishlistService.addProductToWishlist(newWish);
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
	@PostMapping("/ViewWishlist")
	public String getViewWishlist(@RequestBody Map<String, Object> requestData) throws WishlistException {

		String userId = requestData.get("userId").toString();
		
		
		List<ProductDTO> wishprod = wishlistService.viewWishlist(userId);
		System.out.println(wishprod);
		
		JsonArray dataList = new JsonArray();
		try {
			
			for (ProductDTO prod : wishprod) {
				
				System.out.println(prod);
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
				dataList.add(productObj);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataList.toString();
	}


}
