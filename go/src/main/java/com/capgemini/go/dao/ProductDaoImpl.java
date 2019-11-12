package com.capgemini.go.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.dto.ProductIdentityDTO;
import com.capgemini.go.dto.ProductUINMapDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.ProductException;
import com.capgemini.go.utility.GoLog;
import com.capgemini.go.utility.InfoConstants;

@Repository(value = "productDao")
public class ProductDaoImpl implements ProductDao {

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
	 * - Function Name : viewAllProducts - Input Parameters : - Return Type :
	 * List<ProductDTO> - Throws : ProductException - Author : AGNIBHA CHANDRA -
	 * Creation Date : 21/9/2019 - Description : to get all the product from the
	 * database
	 * 
	 * @throws ProductException
	 ********************************************************************************************************/

	public List<ProductDTO> viewAllProducts() throws ProductException {

		List<ProductDTO> allProducts = null;
		Session session = null;
		CriteriaBuilder criteriaBuilder = null;
		Transaction transaction = null;
		try {
			session = getSessionFactory().openSession();
			criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ProductDTO> criteriaQuery = criteriaBuilder.createQuery(ProductDTO.class);
			Root<ProductDTO> product = criteriaQuery.from(ProductDTO.class);
			criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(product.get("quantity"), 0));
			criteriaQuery.orderBy(criteriaBuilder.asc(product.get("productName")));
			allProducts = session.createQuery(criteriaQuery).getResultList();

		} catch (Exception exp) {
			exp.printStackTrace();
			throw new ProductException(ExceptionConstants.VIEW_PRODUCT_ERROR + exp.getMessage());

		} finally {
			session.close();
		}
		return allProducts;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : addProduct - Input Parameters : <ProductDTO> product -
	 * Return Type : boolean - Throws : ProductException - Author : AGNIBHA CHANDRA
	 * - Creation Date : 21/9/2019 - Description : to add a product in product
	 * database
	 * 
	 ********************************************************************************************************/

	@Override
	public boolean addProduct(ProductDTO product) throws ProductException {
		boolean addProductStatus = false;
		Session session = null;
		Transaction transaction = null;
		ProductDTO existingProd = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			existingProd = session.find(ProductDTO.class, product.getProductId());
			if (existingProd != null) {
				GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.PRODUCT_EXISTS);
				throw new ProductException(ExceptionConstants.PRODUCT_EXISTS);
			}
			session.save(product);
			GoLog.getLogger(ProductDaoImpl.class).info(InfoConstants.Product_Added_Success);
			for (int index = 1; index <= product.getQuantity(); index++) {
				ProductIdentityDTO prodIdentity = new ProductIdentityDTO();
				prodIdentity.setProductId(product.getProductId());
				prodIdentity.setProductUIN(product.getProductId() + Integer.toString(index));
				ProductUINMapDTO prodUin = new ProductUINMapDTO(prodIdentity, true);
				session.save(prodUin);
				GoLog.getLogger(ProductDaoImpl.class)
						.info(InfoConstants.Product_Item_Added_Success + Integer.toString(index));
			}
			transaction.commit();
			addProductStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.PRODUCT_ADD_ERROR);
			throw new ProductException(ExceptionConstants.PRODUCT_ADD_ERROR + exp.getMessage());
		} finally {

			session.close();
		}
		return addProductStatus;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : editProduct - Input Parameters : <ProductDTO> product -
	 * Return Type : boolean - Throws : ProductException - Author : AGNIBHA CHANDRA
	 * - Creation Date : 21/9/2019 - Description : to edit a product in product
	 * database
	 * 
	 ********************************************************************************************************/
	@Override
	public boolean editProduct(ProductDTO product) throws ProductException {
		boolean editProductStatus = false;
		Session session = null;
		Transaction transaction = null;
		ProductDTO existingProd = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			existingProd = session.find(ProductDTO.class, product.getProductId());
			if (existingProd == null) {
				GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.PRODUCT_NOT_EXISTS);
				throw new ProductException(ExceptionConstants.PRODUCT_NOT_EXISTS);
			}
			existingProd.setColour(product.getColour());
			existingProd.setDimension(product.getDimension());
			existingProd.setManufacturer(product.getManufacturer());
			existingProd.setPrice(product.getPrice());
			existingProd.setProductCategory(product.getProductCategory());
			existingProd.setProductName(product.getProductName());
			existingProd.setSpecification(product.getSpecification());
			int initialQuantity = existingProd.getQuantity();
			int finalQuantity = initialQuantity + product.getQuantity();
			existingProd.setQuantity(finalQuantity);

			GoLog.getLogger(ProductDaoImpl.class).info(InfoConstants.Product_Update_Success);
			for (int index = 1; index <= product.getQuantity(); index++) {
				ProductIdentityDTO prodIdentity = new ProductIdentityDTO();
				prodIdentity.setProductId(product.getProductId());
				prodIdentity.setProductUIN(product.getProductId() + Integer.toString(index + initialQuantity));
				ProductUINMapDTO prodUin = new ProductUINMapDTO(prodIdentity, true);
				session.save(prodUin);
				GoLog.getLogger(ProductDaoImpl.class)
						.info(InfoConstants.Product_Item_Added_Success + Integer.toString(index + initialQuantity));
			}
			transaction.commit();
			editProductStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.PRODUCT_UPDATE_ERROR);
			throw new ProductException(ExceptionConstants.PRODUCT_UPDATE_ERROR + exp.getMessage());
		} finally {

			session.close();
		}
		return editProductStatus;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : deleteProduct - Input Parameters : String productId Return
	 * Type : boolean - Throws : ProductException - Author : AGNIBHA CHANDRA -
	 * Creation Date : 21/9/2019 - Description : to remove a product in product
	 * database
	 * 
	 ********************************************************************************************************/
	@Override
	public boolean deleteProduct(String productId) throws ProductException {
		boolean deleteProductStatus = false;
		Session session = null;
		Transaction transaction = null;
		ProductDTO existingProd = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			existingProd = session.find(ProductDTO.class, productId);
			if (existingProd == null) {
				GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.PRODUCT_NOT_EXISTS);
				throw new ProductException(ExceptionConstants.PRODUCT_NOT_EXISTS);
			}
			
			int quantity = existingProd.getQuantity();
			existingProd.setQuantity(-1);

			GoLog.getLogger(ProductDaoImpl.class).info(InfoConstants.Product_Delete_Success);
			for (int index = 1; index <= quantity; index++) {
				ProductIdentityDTO prodIdentity = new ProductIdentityDTO();
				prodIdentity.setProductId(productId);
				prodIdentity.setProductUIN(productId + Integer.toString(index));
				ProductUINMapDTO prodItem = session.find(ProductUINMapDTO.class, prodIdentity);
				prodItem.setProductIsPresent(false);
				GoLog.getLogger(ProductDaoImpl.class)
						.info(InfoConstants.Product_Item_Delete_Success + Integer.toString(index));
			}
			transaction.commit();
			deleteProductStatus = true;
		} catch (Exception exp) {
			transaction.rollback();
			GoLog.getLogger(ProductDaoImpl.class).error(ExceptionConstants.PRODUCT_DELETE_ERROR);
			throw new ProductException(ExceptionConstants.PRODUCT_DELETE_ERROR + exp.getMessage());
		} finally {

			session.close();
		}
		return deleteProductStatus;
	}

}
