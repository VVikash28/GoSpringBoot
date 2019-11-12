package com.capgemini.go.dao;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.dto.ProductIdentityDTO;
import com.capgemini.go.dto.ProductUINMapDTO;
import com.capgemini.go.dto.WishlistDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.ProductException;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.exception.WishlistException;
import com.capgemini.go.utility.GoLog;
import com.capgemini.go.utility.InfoConstants;

@Repository(value = "wishlistDao")
public class WishlistDaoImpl implements WishlistDao {

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
			GoLog.getLogger(WishlistDaoImpl.class).info(InfoConstants.Product_Added_Success);
			transaction.commit();
			addProductToWishlistStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			GoLog.getLogger(WishlistDaoImpl.class).error(ExceptionConstants.PRODUCT_ADD_ERROR);
			throw new WishlistException(ExceptionConstants.PRODUCT_ADD_ERROR + ExceptionConstants.PRODUCT_IN_WISHLIST);
		} finally {

			session.close();
		}
		return addProductToWishlistStatus;
	}
}
