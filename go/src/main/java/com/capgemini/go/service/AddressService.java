package com.capgemini.go.service;

import java.util.List;

import com.capgemini.go.dto.AddressDTO;
import com.capgemini.go.exception.AddressException;

public interface AddressService{

	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * - Function Name : addAddress - Input Parameters : <AddressDTO> address -
		 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
		 * Creation Date : 21/9/2019 - Description : to add a address in address
		 * database
		 ********************************************************************************************************/
	public boolean addAddress(AddressDTO address) throws AddressException;
		
	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : updateAddress - Input Parameters : <AddressDTO> address -
	 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
	 * Creation Date : 21/9/2019 - Description : to update address in address
	 * database
	 ********************************************************************************************************/
	public boolean updateAddress(AddressDTO address) throws AddressException;

	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * - Function Name : deleteAddress - Input Parameters : <AddressDTO> address -
		 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
		 * Creation Date : 21/9/2019 - Description : to delete address in address
		 * database
		 ********************************************************************************************************/
	public boolean deleteAddress(String addressId) throws AddressException;
	
	// ------------------------ GreatOutdoor Application --------------------------
			/*******************************************************************************************************
			 * - Function Name : viewAllAddress - Input Parameters : <AddressDTO> address -
			 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
			 * Creation Date : 21/9/2019 - Description : to view all address in address
			 * database
			 ********************************************************************************************************/
	List<AddressDTO> viewAllAddress(String userId) throws AddressException;
}
