package com.capgemini.go.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.service.UserService;
import com.capgemini.go.utility.Authentication;
import com.capgemini.go.utility.InfoConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/User")

public class UserController {

	@Autowired
	private UserService userService;

	// Getters and Setters

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@ResponseBody
	@PostMapping("/registration")
	public String registration(@RequestBody Map<String, Object> requestData) {

		String userName = requestData.get("userName").toString();
		String userId = requestData.get("userId").toString();
		String userMail = requestData.get("userMail").toString();
		String password = requestData.get("password").toString();
		long userNumber = Long.parseLong(requestData.get("userNumber").toString());
		int userCategory = Integer.parseInt(requestData.get("userCategory").toString());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			password = Authentication.encrypt(password, InfoConstants.secretKey);
			UserDTO newUser = new UserDTO(userName, userId, userMail, password, userNumber, userCategory, false);
			result = userService.userRegistration(newUser);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.User_Added_Success);
			} else {
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.USER_REG_ERROR);
			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}

		return dataResponse.toString();

	}

	@ResponseBody
	@PostMapping("/prodMastRegistration")
	public String addProductMaster(@RequestBody Map<String, Object> requestData) {

		String userName = requestData.get("userName").toString();
		String userId = requestData.get("userId").toString();
		String userMail = requestData.get("userMail").toString();
		String password = requestData.get("password").toString();
		long userNumber = Long.parseLong(requestData.get("userNumber").toString());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			password = Authentication.encrypt(password, InfoConstants.secretKey);
			UserDTO newUser = new UserDTO(userName, userId, userMail, password, userNumber, 4, false);
			result = userService.userRegistration(newUser);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.User_Added_Success);
			} else {
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.USER_REG_ERROR);
			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}

		return dataResponse.toString();

	}

	@ResponseBody
	@PostMapping("/login")
	public String login(@RequestBody Map<String, Object> requestData) {

		String userId = requestData.get("userId").toString();
		String password = requestData.get("password").toString();
		UserDTO existUser = new UserDTO(null, userId, null, password, 0L, 2, false);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		UserDTO result = null;
		String userFetched = null;
		try {
			result = userService.userLogin(existUser);
			if (result != null) {
				((ObjectNode) dataResponse).put("Success :", "User Successfully Logged In");

				userFetched = mapper.writeValueAsString(result);
				dataResponse = mapper.readTree(userFetched);

			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}

		return dataResponse.toString();

	}
	
	@ResponseBody
	@PostMapping("/logout")
	public String logout(@RequestBody Map<String, Object> requestData) {

		String userId = requestData.get("userId").toString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		boolean result = false;
		try {
			result = userService.logout(userId);
			if (result == true) {
				((ObjectNode) dataResponse).put("Success :", InfoConstants.User_Logout_Success);

			}
			else
			{
				((ObjectNode) dataResponse).put("Error :", ExceptionConstants.USER_LOGOUT_ERROR);
			}
		} catch (Exception exp) {
			((ObjectNode) dataResponse).put("Error :", exp.getMessage());
		}

		return dataResponse.toString();

	}

}
