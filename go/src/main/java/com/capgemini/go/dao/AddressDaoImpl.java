package com.capgemini.go.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.go.dto.AddressDTO;
import com.capgemini.go.exception.AddressException;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.utility.InfoConstants;
import com.capgemini.go.utility.ValidationUtil;

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
		ValidationUtil.initialiseCityList();
		ValidationUtil.initialiseStateList();
		ValidationUtil.initialiseCountryList();
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

			if (ValidationUtil.getCityList().contains(address.getCity())) {
				addressDto.setCity(address.getCity());
			} else {
				throw new Exception("Enter correct City");
			}

			if (ValidationUtil.getStateList().contains(address.getState())) {
				addressDto.setState(address.getState());
			} else {
				throw new Exception("Enter correct state");
			}
			addressDto.setCountry(address.getCountry());
			if (ValidationUtil.getCountryList().contains(address.getCountry())) {
				addressDto.setCountry(address.getCountry());
			} else {
				throw new Exception("Enter correct Country");
			}
			addressDto.setZip(address.getZip());
			session.save(address);
			logger.info(InfoConstants.Address_Added_Success);
			transaction.commit();
			addAddressStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			logger.error(ExceptionConstants.ADDRESS_ADD_ERROR);
			exp.printStackTrace();
			throw new AddressException(ExceptionConstants.ADDRESS_ADD_ERROR + exp.getMessage());
		} finally {

			session.close();
		}
		return addAddressStatus;
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
		boolean updateAddressStatus = false;
		Session session = null;
		Transaction transaction = null;
		AddressDTO addressDto = new AddressDTO();
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			Query query = session.createQuery(
					"update AddressDTO set buildingNo=:buildno, city=:city, state=:state, country=:country, zip=:zip where addressId=:addressId");
			query.setParameter("buildno", address.getBuildingNo());
			query.setParameter("city", address.getCity());
			query.setParameter("state", address.getState());
			query.setParameter("country", address.getCountry());
			query.setParameter("zip", address.getZip());
			query.setParameter("addressId", address.getAddressId());
			System.out.println("before update");
			int rows = query.executeUpdate();
			System.out.println("after update");
			if(rows!=0)
			{
				updateAddressStatus = true;
			}

			logger.info(InfoConstants.Address_Update_Success);
			transaction.commit();
		
		} catch (Exception exp) {
			transaction.rollback();
			logger.error(ExceptionConstants.ADDRESS_UPDATE_ERROR);
			throw new AddressException(ExceptionConstants.ADDRESS_UPDATE_ERROR + exp.getMessage());
		} finally {

			session.close();
		}
		return updateAddressStatus;
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
		boolean deleteAddressStatus = false;
		Session session = null;
		CriteriaBuilder criteriaBuilder = null;
		Transaction transaction = null;

		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			Query query = session.createQuery("delete from AddressDTO where addressId=:addressId");
			query.setParameter("addressId", addressId);
			query.executeUpdate();

			logger.info(InfoConstants.Address_Delete_Success);
			transaction.commit();
			deleteAddressStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			logger.error(ExceptionConstants.ADDRESS_DELETE_ERROR);
			throw new AddressException(ExceptionConstants.ADDRESS_DELETE_ERROR + exp.getMessage());
		} finally {

			session.close();
		}
		return deleteAddressStatus;
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

		List<AddressDTO> allAddress = null;
		Session session = null;
		CriteriaBuilder criteriaBuilder = null;
		Transaction transaction = null;
		try {
			session = getSessionFactory().openSession();
			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<AddressDTO> criteriaQuery = criteriaBuilder.createQuery(AddressDTO.class);
			Root<AddressDTO> address = criteriaQuery.from(AddressDTO.class);
			criteriaQuery.where(criteriaBuilder.equal(address.get("retailerId"), userId));
			allAddress = session.createQuery(criteriaQuery).getResultList();

		} catch (Exception exp) {
			exp.printStackTrace();
			throw new AddressException(ExceptionConstants.VIEW_ADDRESS_ERROR + exp.getMessage());

		} finally {
			session.close();
		}

		return allAddress;
	}

}
