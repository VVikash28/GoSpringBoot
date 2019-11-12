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

}
