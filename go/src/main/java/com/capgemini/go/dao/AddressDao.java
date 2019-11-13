package com.capgemini.go.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.capgemini.go.dto.AddressDTO;
import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.exception.AddressException;
import com.capgemini.go.exception.ProductException;

@Component
public interface AddressDao {
	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : addAddress - Input Parameters : <AddressDTO> address -
	 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
	 * Creation Date : 21/9/2019 - Description : to add a address in address
	 * database
	 ********************************************************************************************************/
	boolean addAddress(AddressDTO address) throws AddressException;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : updateAddress - Input Parameters : <AddressDTO> address -
	 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
	 * Creation Date : 21/9/2019 - Description : to update address in address
	 * database
	 ********************************************************************************************************/
	boolean updateAddress(AddressDTO address) throws AddressException;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : deleteAddress - Input Parameters : <AddressDTO> address -
	 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
	 * Creation Date : 21/9/2019 - Description : to delete address in address
	 * database
	 ********************************************************************************************************/
	boolean deleteAddress(String addressId) throws AddressException;
	
	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * - Function Name : viewAllAddress - Input Parameters : <AddressDTO> address -
		 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
		 * Creation Date : 21/9/2019 - Description : to view all address in address
		 * database
		 ********************************************************************************************************/
	List<AddressDTO> viewAllAddress(String userId) throws AddressException;

}
