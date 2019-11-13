package com.capgemini.go.service;

import com.capgemini.go.dao.AddressDao;
import com.capgemini.go.dao.ProductDao;
import com.capgemini.go.dto.AddressDTO;
import com.capgemini.go.exception.AddressException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "addressService")

public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressDao addressDao;

	public AddressDao getAddressDao() {
		return addressDao;
	}

	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	// ------------------------ GreatOutdoor Application --------------------------
			/*******************************************************************************************************
			 * - Function Name : addAddress - Input Parameters : <AddressDTO> address -
			 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
			 * Creation Date : 21/9/2019 - Description : to add a address in address
			 * database
			 ********************************************************************************************************/
	@Override
	public boolean addAddress(AddressDTO address) throws AddressException {
		return addressDao.addAddress(address);
	}

	// ------------------------ GreatOutdoor Application --------------------------
		/*******************************************************************************************************
		 * - Function Name : updateAddress - Input Parameters : <AddressDTO> address -
		 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
		 * Creation Date : 21/9/2019 - Description : to update address in address
		 * database
		 ********************************************************************************************************/
	@Override
	public boolean updateAddress(AddressDTO address) throws AddressException {
		return addressDao.updateAddress(address);
	}

	// ------------------------ GreatOutdoor Application --------------------------
			/*******************************************************************************************************
			 * - Function Name : deleteAddress - Input Parameters : <AddressDTO> address -
			 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
			 * Creation Date : 21/9/2019 - Description : to delete address in address
			 * database
			 ********************************************************************************************************/
	@Override
	public boolean deleteAddress(String addressId) throws AddressException {
		return addressDao.deleteAddress(addressId);
	}

	// ------------------------ GreatOutdoor Application --------------------------
			/*******************************************************************************************************
			 * - Function Name : viewAllAddress - Input Parameters : <AddressDTO> address -
			 * Return Type : boolean - Throws : AddressException - Author : AYUSHI DIXIT -
			 * Creation Date : 21/9/2019 - Description : to view all address in address
			 * database
			 ********************************************************************************************************/
	@Override
	public List<AddressDTO> viewAllAddress(String userId) throws AddressException {
				
		return addressDao.viewAllAddress(userId);
	}

}
