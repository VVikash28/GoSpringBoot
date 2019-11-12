package com.capgemini.go.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.UserException;

@Component
public interface UserDao {

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : userRegistration - Input Parameters : userID, userName,
	 * userMail, userNumber, activeStatus, password, userCategory - Return Type :
	 * boolean - Throws :UserException - Author:AMAN - Creation Date : 21/9/2019 -
	 * Description : to register a new user
	 * 
	 * 
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

	boolean prodMastRegistration(UserDTO user) throws UserException;

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

	/*******************************************************************************************************
	 * - Function Name : getUserById - Input Parameters : userID - Return Type
	 * : UserDTO - Throws :UserException - Author : Kunal - Creation Date :21/9/2019
	 * - Description : to logout a user
	 * 
	 * @throws UserException
	 * @throws Exception
	 ********************************************************************************************************/
	UserDTO getUserById (String userId) throws UserException;
	
	/*******************************************************************************************************
	 * - Function Name : getUserIdList - Input Parameters : - Return Type
	 * : UserDTO - Throws :UserException - Author : Kunal - Creation Date :21/9/2019
	 * - Description : to logout a user
	 * 
	 * @throws UserException
	 * @throws Exception
	 ********************************************************************************************************/
	List<UserDTO> getUserIdList () throws UserException;
}
