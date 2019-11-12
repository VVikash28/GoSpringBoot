package com.capgemini.go.service;

import java.net.ConnectException;
import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.go.dao.ProductDao;
import com.capgemini.go.dao.UserDao;
import com.capgemini.go.dao.UserDaoImpl;
import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.UserException;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao user;

	public UserDao getUserDao() {
		return user;
	}

	public void setUserDao(UserDao user) {
		this.user = user;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : userRegistration - Input Parameters : userID, userName,
	 * userMail, userNumber, activeStatus, password, userCategory - Return Type :
	 * boolean - Author : CAPGEMINI - Creation Date : 21/9/2019 - Description : to
	 * register a new user
	 * 
	 * @throws UserException
	 * @throws ConnectException
	 ********************************************************************************************************/

	public boolean userRegistration(UserDTO newuser) throws UserException {

		return user.userRegistration(newuser);
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : prodMastRegistration - Input Parameters : <UserDTO> user -
	 * Return Type : boolean - Throws :UserException - Author:AGNIBHA CHANDRA -
	 * Creation Date : 21/9/2019 - Description : to register a new product Master
	 * 
	 * 
	 ********************************************************************************************************/

	public boolean prodMastRegistration(UserDTO productMaster) throws UserException {

		return user.prodMastRegistration(productMaster);
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : userLogin - Input Parameters : userID, password - Return
	 * Type : boolean - Throws :UserException - Author : AMAN - Creation Date
	 * :21/9/2019 - Description : to login a user
	 * 
	 * @throws UserException
	 * @throws Exception
	 ********************************************************************************************************/
	public UserDTO userLogin(UserDTO newuser) throws UserException {
		return user.userLogin(newuser);
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : logout - Input Parameters : userID, password - Return Type
	 * : boolean - Throws :UserException - Author : AMAN - Creation Date :21/9/2019
	 * - Description : to logout a user
	 * 
	 * @throws UserException
	 * @throws Exception
	 ********************************************************************************************************/
	public boolean logout(String userId) throws UserException {
		return user.logout(userId);
	}

}
