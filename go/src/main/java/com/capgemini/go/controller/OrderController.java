package com.capgemini.go.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.dto.CartDTO;
import com.capgemini.go.dto.OrderDTO;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.service.OrderAndCartService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Cart")
public class OrderController {

	@Autowired
	private OrderAndCartService orderAndCartService;

	// Getters and Setters

	public OrderAndCartService getOrderAndCartService() {
		return orderAndCartService;
	}

	public void setOrderAndCartService(OrderAndCartService orderAndCartService) {
		this.orderAndCartService = orderAndCartService;
	}

	@ResponseBody
	@PostMapping(value = "/AddItem")
	public String addItemtoCart(@RequestBody Map<String, Object> requestData) {
		CartDTO cartItem = new CartDTO();
		cartItem.setProductId(requestData.get("addItemProdId").toString());
		cartItem.setUserId(requestData.get("addItemuserId").toString());
		cartItem.setQuantity(Integer.parseInt(requestData.get("addItemProdQty").toString()));
		boolean cart;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		try {
			cart = orderAndCartService.addItemToCart(cartItem);

			if (cart) {

				((ObjectNode) dataResponse).put("Success :", "Item Added Successfully");
			} else {
				((ObjectNode) dataResponse).put("Error :", "Error in adding item to cart");
			}
		} catch (RetailerException e) {

			((ObjectNode) dataResponse).put("Error :", "Item already added to cart");
		}

		return dataResponse.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/BuyNowServlet", method = RequestMethod.POST)
	public String placeOrder(@RequestBody Map<String, Object> requestData) {
		OrderDTO ord = new OrderDTO();
		ord.setUserId(String.valueOf(requestData.get("placeOrderCustId")));
		System.out.println(requestData.get("placeOrderCustId"));
		ord.setAddressId(String.valueOf(requestData.get("placeOrderAddrId")));
		boolean order = true;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		try {

			order = orderAndCartService.registerOrder(ord);

			if (order) {
				((ObjectNode) dataResponse).put("Success :", "Order placed Successfully");
			} else {
				((ObjectNode) dataResponse).put("Success :", "Error in placing the order");
			}
		} catch (RetailerException e) {

			e.printStackTrace();
		}

		return dataResponse.toString();
	}

}