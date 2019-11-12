package com.capgemini.go.dao;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.utility.Authentication;
import com.capgemini.go.utility.GoLog;
import com.capgemini.go.utility.InfoConstants;

@Repository(value = "userDao")
public class UserDaoImpl implements UserDao {

	// this class is wired with the sessionFactory to do some operation in the
	// database

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
	 * - Function Name : userRegistration - Input Parameters : userID, userName,
	 * userMail, userNumber, activeStatus, password, userCategory - Return Type :
	 * boolean - Throws :UserException - Author:AMAN - Creation Date : 21/9/2019 -
	 * Description : to register a new user
	 * 
	 * @throws ConnectException
	 * @throws HibernateException
	 ********************************************************************************************************/

	public boolean userRegistration(UserDTO user) throws UserException {

		boolean userRegistrationStatus = false;
		Session session = null;
		Transaction transaction = null;
		CriteriaBuilder criteriaBuilder = null;
		UserDTO existingUser = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			existingUser = session.find(UserDTO.class, user.getUserId());
			if (existingUser != null) {
				GoLog.getLogger(UserDaoImpl.class).error(ExceptionConstants.USER_EXISTS);
				throw new UserException(ExceptionConstants.USER_EXISTS);
			}

			List<UserDTO> u = new ArrayList<UserDTO>();
			// Checking unique mail
			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<UserDTO> criteriaQuery = criteriaBuilder.createQuery(UserDTO.class);
			Root<UserDTO> existuser = criteriaQuery.from(UserDTO.class);
			criteriaQuery.where(criteriaBuilder.equal(existuser.get("userMail"), user.getUserMail()));
			u = session.createQuery(criteriaQuery).getResultList();
			if (u.size() != 0) {
				GoLog.getLogger(UserDaoImpl.class).error(ExceptionConstants.USER_MAIL_EXISTS);
				throw new UserException(ExceptionConstants.USER_MAIL_EXISTS);
			}
			
			// Checking unique number
			criteriaBuilder = session.getCriteriaBuilder();
			criteriaQuery = criteriaBuilder.createQuery(UserDTO.class);
			existuser = criteriaQuery.from(UserDTO.class);
			criteriaQuery.where(criteriaBuilder.equal(existuser.get("userNumber"), user.getUserNumber()));
			u = session.createQuery(criteriaQuery).getResultList();
			if (u.size() != 0) {
				GoLog.getLogger(UserDaoImpl.class).error(ExceptionConstants.USER_NUMBER_EXISTS);
				throw new UserException(ExceptionConstants.USER_NUMBER_EXISTS);
			}

			session.save(user);
			GoLog.getLogger(ProductDaoImpl.class).info(InfoConstants.User_Added_Success);
			transaction.commit();
			userRegistrationStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			GoLog.getLogger(UserDaoImpl.class).error(ExceptionConstants.USER_REG_ERROR);
			throw new UserException(ExceptionConstants.USER_REG_ERROR + exp.getMessage());
		} finally {

			session.close();
		}
		return userRegistrationStatus;

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

		return userRegistration(productMaster);
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
	public UserDTO userLogin(UserDTO user) throws UserException {
		boolean userLoginStatus = false;
		Session session = null;
		Transaction transaction = null;
		UserDTO existingUser = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			existingUser = session.find(UserDTO.class, user.getUserId());
			if (existingUser == null) {
				GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.USER_NOT_EXISTS);
				throw new UserException(ExceptionConstants.USER_NOT_EXISTS);
			}
			if (existingUser.isUserActiveStatus() == true) {
				GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.ALREADY_LOGGEDIN);
				throw new UserException(ExceptionConstants.ALREADY_LOGGEDIN);
			}

			if (Authentication.encrypt(user.getUserPassword(), InfoConstants.secretKey)
					.equals(existingUser.getUserPassword())) {
				existingUser.setUserActiveStatus(true);
				GoLog.getLogger(ProductDaoImpl.class).info(InfoConstants.User_Login_Success);
				transaction.commit();
				userLoginStatus = true;
			}

			else {
				GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.PASSWORD_MISMATCH);
				throw new UserException(ExceptionConstants.PASSWORD_MISMATCH);
			}
		} catch (Exception exp) {
			transaction.rollback();
			GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.USER_LOGIN_ERROR);
			throw new UserException(ExceptionConstants.USER_LOGIN_ERROR + exp.getMessage());
		} finally {

			session.close();
		}

		return existingUser;

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
		boolean userLogoutStatus = false;
		Session session = null;
		Transaction transaction = null;
		UserDTO existingUser = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			existingUser = session.find(UserDTO.class, userId);
			if (existingUser == null) {
				GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.USER_NOT_EXISTS);
				throw new UserException(ExceptionConstants.USER_NOT_EXISTS);
			}
			existingUser.setUserActiveStatus(false);
			transaction.commit();
			userLogoutStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.USER_LOGOUT_ERROR);
			throw new UserException(ExceptionConstants.USER_LOGOUT_ERROR + exp.getMessage());
		} finally {

			session.close();
		}

		return userLogoutStatus;
	}

	/*******************************************************************************************************
	 * - Function Name : getUserById - Input Parameters : userID - Return Type
	 * : UserDTO - Throws :UserException - Author : Kunal - Creation Date :21/9/2019
	 * - Description : to logout a user
	 * 
	 * @throws UserException
	 * @throws Exception
	 ********************************************************************************************************/
	public UserDTO getUserById (String userId) throws UserException {
		Transaction transaction = null;
		Session session = null;
		UserDTO existingUser = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			existingUser = session.find(UserDTO.class, userId);
			if (existingUser == null) {
				GoLog.getLogger(UserDaoImpl.class).error(ExceptionConstants.USER_NOT_EXISTS);
				throw new UserException(ExceptionConstants.USER_NOT_EXISTS);
			}
			transaction.commit();
		} catch (Exception exp) {
			transaction.rollback();
		} finally {
			session.close();
		}
		return existingUser;
	}
	
	/*******************************************************************************************************
	 * - Function Name : getUserIdList - Input Parameters : - Return Type
	 * : UserDTO - Throws :UserException - Author : Kunal - Creation Date :21/9/2019
	 * - Description : to logout a user
	 * 
	 * @throws UserException
	 * @throws Exception
	 ********************************************************************************************************/
	public List<UserDTO> getUserIdList () throws UserException {
		List<UserDTO> result = null;
		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<UserDTO> criteriaQuery = builder.createQuery(UserDTO.class);
			Root<UserDTO> userTable = criteriaQuery.from(UserDTO.class);
			criteriaQuery.select(userTable);
			result = session.createQuery(criteriaQuery).getResultList();
			transaction.commit();
		} catch (Exception exp) {
			transaction.rollback();
			GoLog.getLogger(UserDaoImpl.class).error("getUserIdList - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
			throw new UserException ("getUserIdList - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
		} finally {
			session.close();
		}
		return result;
	}
}
