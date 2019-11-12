package com.capgemini.go.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.go.dto.AddressDTO;
import com.capgemini.go.exception.AddressException;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.utility.InfoConstants;

@Repository(value = "addressDao")
public class AddressDaoImpl implements AddressDao {
	
	private Logger logger = Logger.getRootLogger();
	
	@Autowired
	private SessionFactory sessionFactory;
	// this will create one sessionFactory for this class
	// there is only one sessionFactory should be created for the applications
	// we can create multiple sessions for a sessionFactory
	// each session can do some functions

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
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

		boolean addAddressStatus = false;
		Session session = null;
		Transaction transaction = null;
		AddressDTO addressDto = new AddressDTO();
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			addressDto.setAddressId(address.getAddressId());
			addressDto.setRetailerId(address.getRetailerId());
			addressDto.setBuildingNo(address.getBuildingNo());
			addressDto.setCity(address.getCity());
			addressDto.setState(address.getState());
			addressDto.setCountry(address.getCountry());
			addressDto.setZip(address.getZip());
			session.save(address);
			logger.info(InfoConstants.Address_Added_Success);
			transaction.commit();
			addAddressStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			logger.error(ExceptionConstants.ADDRESS_ADD_ERROR);
			throw new AddressException(ExceptionConstants.ADDRESS_ADD_ERROR + exp.getMessage());
		} finally {

			session.close();
		}
		return addAddressStatus;
	}

}
