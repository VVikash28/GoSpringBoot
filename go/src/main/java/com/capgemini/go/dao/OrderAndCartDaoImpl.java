package com.capgemini.go.dao;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.go.dto.CartDTO;
import com.capgemini.go.dto.OrderDTO;
import com.capgemini.go.dto.OrderProductMapDTO;
import com.capgemini.go.dto.ProductUINMapDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.RetailerException;


@Repository(value = "orderAndCartDao")
public class OrderAndCartDaoImpl implements OrderAndCartDao {
	private Logger logger = Logger.getRootLogger();
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// ORDER TABLE MANIPULATION FUNCTIONS
	/*******************************************************************************************************
	 * Function Name : addItemToCart <br>
	 * Input Parameters : CartDTO (retailerId, productId, quantity) <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 27/9/2019 <br>
	 * Description : to add item to a cart <br>
	 ********************************************************************************************************/
	@Override
	public boolean addItemToCart(CartDTO cartItem) throws RetailerException {
		boolean itemAdded = false;

		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			session.save(cartItem);
			transaction.commit();
		} catch (IllegalStateException error) {
			logger.error(error.getMessage());
			throw new RetailerException("addItemToCart - " + ExceptionConstants.INAPPROPRIATE_METHOD_INVOCATION);
		} catch (RollbackException error) {
			logger.error(error.getMessage());
			throw new RetailerException("addItemToCart - " + ExceptionConstants.FAILURE_COMMIT_CHANGES);
		} catch (PersistenceException error) {
			logger.error(error.getMessage());
			throw new RetailerException("addItemToCart - " + ExceptionConstants.ITEM_ALREADY_PRESENT_IN_CART);
		} finally {
			session.close();
		}
		itemAdded = true;
		return itemAdded;
	}

	/*******************************************************************************************************
	 * Function Name : removeItemFromCart <br>
	 * Input Parameters : CartDTO (retailerId, productId)<br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 27/9/2019 <br>
	 * Description : to remove a item from cart <br>
	 ********************************************************************************************************/
	@Override
	public boolean removeItemFromCart(CartDTO cartItem) throws RetailerException {
		boolean itemRemoved = false;

		CartDTO newItem = new CartDTO(cartItem.getUserId(), cartItem.getProductId(), 0);

		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			session.remove(newItem);
			transaction.commit();
		} catch (IllegalStateException error) {
			logger.error(error.getMessage());
			throw new RetailerException("removeItemFromCart - " + ExceptionConstants.INAPPROPRIATE_METHOD_INVOCATION);
		} catch (RollbackException error) {
			logger.error(error.getMessage());
			throw new RetailerException("removeItemFromCart - " + ExceptionConstants.FAILURE_COMMIT_CHANGES);
		} finally {
			session.close();
		}
		itemRemoved = true;
		return itemRemoved;
	}

	/*******************************************************************************************************
	 * Function Name : increaseItemQuantity <br>
	 * Input Parameters : CartDTO (retailerId, productId, quantity(absolute
	 * value|not offset))<br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 27/9/2019 <br>
	 * Description : to increase item quantity in cart <br>
	 ********************************************************************************************************/
	@Override
	public boolean updateItemQuantity(CartDTO cartItem) throws RetailerException {
		boolean itemUpdated = false;

		CartDTO newItem = new CartDTO(cartItem.getUserId(), cartItem.getProductId(), cartItem.getQuantity());
		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			List<CartDTO> itemList = session.createQuery("from CartDTO", CartDTO.class).list();
			boolean productNotFound = true;
			for (CartDTO item : itemList) {
				if (item.getUserId().equals(newItem.getUserId())
						&& item.getProductId().equals(newItem.getProductId())) {
					productNotFound = false;
					break;
				}
			}
			if (productNotFound) {
				logger.debug(ExceptionConstants.ITEM_NOT_IN_CART);
				throw new RetailerException("updateItemQuantity - " + ExceptionConstants.ITEM_NOT_IN_CART);
			} else {
				session.merge(newItem);
			}
			transaction.commit();
		} catch (IllegalStateException error) {
			logger.error(error.getMessage());
			throw new RetailerException("updateItemQuantity - " + ExceptionConstants.INAPPROPRIATE_METHOD_INVOCATION);
		} catch (RollbackException error) {
			logger.error(error.getMessage());
			throw new RetailerException("updateItemQuantity - " + ExceptionConstants.FAILURE_COMMIT_CHANGES);
		} finally {
			session.close();
		}
		itemUpdated = true;
		return itemUpdated;
	}

	/*******************************************************************************************************
	 * Function Name : placeOrder <br>
	 * Input Parameters : OrderDTO(orderId, userId, addressId, orderInitiationTime)
	 * <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 21/9/2019 <br>
	 * Description : to register an order for items in the cart <br>
	 ********************************************************************************************************/
	public boolean registerOrder(OrderDTO order) throws RetailerException {
		boolean orderRegistered = false;

		OrderDTO newOrder = new OrderDTO(order.getOrderId(), (byte) 0, null, order.getUserId(), order.getAddressId(),
				order.getOrderInitiateTime());
		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			session.save(newOrder);
			transaction.commit();
		} catch (IllegalStateException error) {
			logger.error(error.getMessage());
			throw new RetailerException("registerOrder - " + ExceptionConstants.INAPPROPRIATE_METHOD_INVOCATION);
		} catch (RollbackException error) {
			logger.error(error.getMessage());
			throw new RetailerException("registerOrder - " + ExceptionConstants.FAILURE_COMMIT_CHANGES);
		} catch (PersistenceException error) {
			logger.error(error.getMessage());
			throw new RetailerException("registerOrder - " + ExceptionConstants.ITEM_ALREADY_PRESENT_IN_CART);
		} finally {
			session.close();
		}
		orderRegistered = true;
		return orderRegistered;
	}
	// END OF ORDER TABLE MANIPULATION FUNCTIONS

	// ORDER PRODUCT MAP TABLE MANIPULATION FUNCTIONS
	/*******************************************************************************************************
	 * Function Name : insertOrderProductMapEntity <br>
	 * Input Parameters : OrderProductMapDTO(orderId, productId, productUIN,
	 * productStatus, giftStatus) <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 21/9/2019 <br>
	 * Description : to register an item against a particular order <br>
	 ********************************************************************************************************/
	public boolean insertOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) throws RetailerException {
		boolean itemInserted = false;

		OrderProductMapDTO newItem = new OrderProductMapDTO(orderProductMapEntity.getOrderId(),
				orderProductMapEntity.getProductId(), orderProductMapEntity.getProductUIN(),
				orderProductMapEntity.getProductStatus(), orderProductMapEntity.getGiftStatus());

		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			session.save(newItem);
			transaction.commit();
		} catch (IllegalStateException error) {
			logger.error(error.getMessage());
			throw new RetailerException(
					"insertOrderProductMapEntity - " + ExceptionConstants.INAPPROPRIATE_METHOD_INVOCATION);
		} catch (RollbackException error) {
			logger.error(error.getMessage());
			throw new RetailerException("insertOrderProductMapEntity - " + ExceptionConstants.FAILURE_COMMIT_CHANGES);
		} catch (PersistenceException error) {
			logger.error(error.getMessage());
			throw new RetailerException(
					"insertOrderProductMapEntity - " + ExceptionConstants.ITEM_ALREADY_MAPPED_TO_ORDER);
		} finally {
			session.close();
		}
		itemInserted = true;
		return itemInserted;
	}

	/*******************************************************************************************************
	 * Function Name : deleteOrderProductMapEntity <br>
	 * Input Parameters : OrderProductMapDTO(orderId, productId, productUIN) <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 21/9/2019 <br>
	 * Description : to delete an item in a particular order <br>
	 ********************************************************************************************************/
	public boolean deleteOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) throws RetailerException {
		boolean itemDeleted = false;

		OrderProductMapDTO newItem = new OrderProductMapDTO(orderProductMapEntity.getOrderId(),
				orderProductMapEntity.getProductId(), orderProductMapEntity.getProductUIN(), 0, 0);

		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			session.remove(newItem);
			transaction.commit();
		} catch (IllegalStateException error) {
			logger.error(error.getMessage());
			throw new RetailerException(
					"deleteOrderProductMapEntity - " + ExceptionConstants.INAPPROPRIATE_METHOD_INVOCATION);
		} catch (RollbackException error) {
			logger.error(error.getMessage());
			throw new RetailerException("deleteOrderProductMapEntity - " + ExceptionConstants.FAILURE_COMMIT_CHANGES);
		} finally {
			session.close();
		}
		itemDeleted = true;
		return itemDeleted;
	}
	// END OF ORDER PRODUCT MAP TABLE MANIPULATION FUNCTIONS

	// PRODUCT UIN MAP TABLE MANIPULATION FUNCTIONS
	/*******************************************************************************************************
	 * Function Name : updateProductUinMap <br>
	 * Input Parameters : ProductUINMapDTO (productId, productUIN) <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 21/9/2019 <br>
	 * Description : to update present status of an unique item <br>
	 ********************************************************************************************************/
	public boolean updateProductUinMap(ProductUINMapDTO ProductUinMapEntity) throws RetailerException {
		boolean itemUpdated = false;

		ProductUINMapDTO itemTobeUpdated = new ProductUINMapDTO();
		itemTobeUpdated.setProductUniqueIdentity(ProductUinMapEntity.getProductUniqueIdentity());
		itemTobeUpdated.setProductIsPresent(false);

		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			List<ProductUINMapDTO> itemList = session.createQuery("from ProductUINMapDTO", ProductUINMapDTO.class)
					.list();
			boolean productNotFound = true;
			for (ProductUINMapDTO item : itemList) {
				if (item.getProductUniqueIdentity().getProductId()
						.equals(itemTobeUpdated.getProductUniqueIdentity().getProductId())
						&& item.getProductUniqueIdentity().getProductUIN()
								.equals(itemTobeUpdated.getProductUniqueIdentity().getProductUIN())) {
					productNotFound = false;
					break;
				}
			}
			if (productNotFound) {
				logger.debug(ExceptionConstants.PRODUCT_NOT_IN_INVENTORY);
				throw new RetailerException("updateProductUinMap - " + ExceptionConstants.PRODUCT_NOT_IN_INVENTORY);
			} else {
				session.merge(itemTobeUpdated);
			}
			transaction.commit();
		} catch (IllegalStateException error) {
			logger.error(error.getMessage());
			throw new RetailerException("updateProductUinMap - " + ExceptionConstants.INAPPROPRIATE_METHOD_INVOCATION);
		} catch (RollbackException error) {
			logger.error(error.getMessage());
			throw new RetailerException("updateProductUinMap - " + ExceptionConstants.FAILURE_COMMIT_CHANGES);
		} finally {
			session.close();
		}

		itemUpdated = true;
		return itemUpdated;
	}
	// END OF PRODUCT UIN MAP TABLE MANIPULATION FUNCTIONS
}
