package com.capgemini.go.dao;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.dto.WishlistDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.WishlistException;
import com.capgemini.go.utility.InfoConstants;

@Repository(value = "wishlistDao")
public class WishlistDaoImpl implements WishlistDao {

	// this class is wired with the sessionFactory to do some operation in the
	// database

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
	 * - Function Name : addProductToWishlist - Input Parameters : Product List -
	 * Return Type : boolean - Throws : RetailerException - Author : Shalu Panwar -
	 * Creation Date : 21/9/2019 - Description : To add products to Wishlist
	 * database
	 * 
	 * @throws IOException
	 * @throws SQLException
	 ********************************************************************************************************/

	@Override
	public boolean addProductToWishlist(WishlistDTO wishlist) throws WishlistException {

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
			exp.printStackTrace();
			transaction.rollback();
			logger.error(ExceptionConstants.PRODUCT_ADD_ERROR);
			throw new WishlistException(ExceptionConstants.PRODUCT_ADD_ERROR + ExceptionConstants.PRODUCT_IN_WISHLIST);
		} finally {

			session.close();
		}
		return addProductToWishlistStatus;
	}

	/*******************************************************************************************************
	 * - Function Name : viewWishlist - Input Parameters : Product List - Return
	 * Type : boolean - Throws : WishlistException - Author : Shalu Panwar -
	 * Creation Date : 21/9/2019 - Description : To view products in Wishlist
	 * database
	 * 
	 * @throws ConnectException
	 * @throws IOException
	 * @throws HibernateException
	 ********************************************************************************************************/
	@Override
	public List<ProductDTO> viewWishlist(String userId) throws WishlistException {

		List<ProductDTO> allfavproduct = new ArrayList<ProductDTO>();
		Session session = null;
		CriteriaBuilder criteriaBuilder = null;
		Transaction transaction = null;
		ProductDTO temp = null;
		try {

			session = getSessionFactory().openSession();
			criteriaBuilder = session.getCriteriaBuilder();
			transaction = session.getTransaction();
			transaction.begin();

			List<Object[]> results = session.createQuery(HQLQuerryMapper.VIEW_WISHLIST).setParameter("userId", userId)
					.list();

			for (Object[] data : results) {

				System.out.println(data[0].toString());

				allfavproduct.add((ProductDTO) data[0]);
			}

		} catch (Exception exp) {
			exp.printStackTrace();
			throw new WishlistException(ExceptionConstants.WISHLIST_PRODUCT + exp.getMessage());

		} finally {
			session.close();
		}
		return allfavproduct;

	}

}
