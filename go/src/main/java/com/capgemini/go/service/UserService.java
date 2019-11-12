package com.capgemini.go.service;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.UserException;

public interface UserService {

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : userRegistration - Input Parameters : userID, userName,
	 * userMail, userNumber, activeStatus, password, userCategory - Return Type :
	 * boolean - Author : CAPGEMINI - Creation Date : 21/9/2019 - Description : to
	 * register a new user
	 * 
	 * @throws UserException
	 ********************************************************************************************************/
	boolean userRegistration(UserDTO user) throws UserException;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : prodMastRegistration - Input Parameters : <UserDTO> user -
	 * Return Type : boolean - Throws :UserException - Author:AGNIBHA CHANDRA -
	 * Creation Date : 21/9/2019 - Description : to register a new product Master
	 * 
	 * 
	 ********************************************************************************************************/

	boolean prodMastRegistration(UserDTO productMaster) throws UserException;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : userLogin - Input Parameters : userID, password - Return
	 * Type : boolean - Throws :UserException - Author : AMAN - Creation Date
	 * :21/9/2019 - Description : to login a user
	 * 
	 * @throws UserException
	 * @throws Exception
	 ********************************************************************************************************/
	UserDTO userLogin(UserDTO user) throws UserException;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : logout - Input Parameters : userID, password - Return Type
	 * : boolean - Throws :UserException - Author : AMAN - Creation Date :21/9/2019
	 * - Description : to logout a user
	 * 
	 * @throws UserException
	 * @throws Exception
	 ********************************************************************************************************/
	boolean logout(String userId) throws UserException;
}
