package com.capgemini.go.dao;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.go.dto.WishlistDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.WishlistException;
import com.capgemini.go.utility.InfoConstants;

@Repository(value = "wishlistDao")
public class WishlistDaoImpl implements WishlistDao {

	private Logger logger = Logger.getRootLogger();
	
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
	 * - Function Name : addProductToWishlist - Input Parameters : Product List -
	 * Return Type : boolean - Throws : RetailerException - Author : CAPGEMINI -
	 * Creation Date : 21/9/2019 - Description : To add products to Wishlist
	 * database
	 * 
	 * @throws IOException
	 * @throws SQLException
	 ********************************************************************************************************/

	@Override
	public boolean addProductToWishlist(WishlistDTO wishlist) throws WishlistException {
		
		
		System.out.println("okkk");
		boolean addProductToWishlistStatus = false;
		Session session = null;
		Transaction transaction = null;
		WishlistDTO addwishlist = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			addwishlist = session.find(WishlistDTO.class, wishlist.getId());
			session.save(wishlist);
			logger.info(InfoConstants.Product_Added_Success);
			transaction.commit();
			addProductToWishlistStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			logger.error(ExceptionConstants.PRODUCT_ADD_ERROR);
			throw new WishlistException(ExceptionConstants.PRODUCT_ADD_ERROR + ExceptionConstants.PRODUCT_IN_WISHLIST);
		} finally {

			session.close();
		}
		return addProductToWishlistStatus;
	}
}
