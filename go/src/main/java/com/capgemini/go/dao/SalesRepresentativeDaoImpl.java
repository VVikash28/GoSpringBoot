package com.capgemini.go.dao;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.go.dto.OrderCancelDTO;
import com.capgemini.go.dto.OrderDTO;
import com.capgemini.go.dto.OrderProductMapDTO;
import com.capgemini.go.dto.OrderReturnDTO;
import com.capgemini.go.dto.SalesRepDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.SalesRepresentativeException;
import com.capgemini.go.utility.InfoConstants;


@Repository(value = "salesRepresentativeDao")
public class SalesRepresentativeDaoImpl implements SalesRepresentativeDao {

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
		System.out.println("Setting Session Factory - " + sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : returnOrder - Input Parameters : OrderReturnDTO - Return
	 * Type : boolean - Throws :SalesRepresentativeException, ConnectException -
	 * Author : CAPGEMINI - Creation Date : 23/09/2019 - Description : Return order
	 * adds the respective order in the order_return table in the database
	 ********************************************************************************************************/

	@Override
	public boolean returnOrder(OrderReturnDTO or) throws SalesRepresentativeException, ConnectException {
		boolean returnOrderStatus = false;
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			OrderReturnDTO orderReturn = new OrderReturnDTO();
			orderReturn.setOrderId(or.getOrderId());
			orderReturn.setOrderReturnReason(or.getOrderReturnReason());
			orderReturn.setProductId(or.getProductId());
			orderReturn.setProductUIN(or.getProductUIN());
			orderReturn.setUserId(or.getUserId());
			orderReturn.setOrderReturnTime(sqlDate);
			session.save(orderReturn);
			session.getTransaction().commit();
			returnOrderStatus = true;
			logger.info(InfoConstants.Return_Accepted);
		} catch (HibernateException exp) {
			session.getTransaction().rollback();
			logger.error(ExceptionConstants.UNABLE_TO_UPDATE_RETURN_ORDER);
			throw new SalesRepresentativeException(ExceptionConstants.UNABLE_TO_UPDATE_RETURN_ORDER + exp.getMessage());
		} finally {
			session.close();
		}

		return returnOrderStatus;
	}

	/*******************************************************************************************************
	 * - Function Name : updateOrderProductMap - Input Parameters : String orderId -
	 * Return Type : boolean - Throws :SalesRepresentativeException,
	 * ConnectException - Author : CAPGEMINI - Creation Date : 23/09/2019 -
	 * Description : updating Order_Product_Map in the database by setting
	 * product_status=0 for the products that have been returned
	 ********************************************************************************************************/

	public boolean updateOrderProductMap(String orderId) throws SalesRepresentativeException, ConnectException {
		boolean orderProductMapFlag = false;
		int productStatus=1;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			OrderProductMapDTO opm = new OrderProductMapDTO();
			Query query = session.createQuery(HQLQuerryMapper.UPDATE_ORDER_PRODUCT_MAP);
			query.setParameter("orderId", orderId);
			query.setParameter("productStatus", productStatus);
			int result = query.executeUpdate();
			orderProductMapFlag = true;
			logger.info(InfoConstants.UpdatedOrderProductMap);
			session.getTransaction().commit();
		} catch (HibernateException exp) {
			session.getTransaction().rollback();
			logger.error(ExceptionConstants.ORDER_PRODUCT_MAP_UPDATE_FAILURE);
			throw new SalesRepresentativeException(ExceptionConstants.ORDER_PRODUCT_MAP_UPDATE_FAILURE + exp.getMessage());
		} finally {
			session.close();
		}
		return orderProductMapFlag;

	}

	/*******************************************************************************************************
	 * - Function Name : getOrderProductMap - Input Parameters : String orderId -
	 * Return Type :List<ORderProductMap> - Throws :SalesRepresentativeException,
	 * ConnectException - Author : CAPGEMINI - Creation Date : 23/09/2019 -
	 * Description : getting all the products against a particular order
	 ********************************************************************************************************/
	@Override
	public List<OrderProductMapDTO> getOrderProductMap(String orderId)
			throws SalesRepresentativeException, ConnectException {
		List<OrderProductMapDTO> orderProductMap = new ArrayList<OrderProductMapDTO>();
		Session session = null;
		SessionFactory sessionFactory = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			List<OrderProductMapDTO> opmEntity = new ArrayList<OrderProductMapDTO>();
			Query query = session.createQuery(HQLQuerryMapper.GET_ORDER_PRODUCT_MAP);
			query.setParameter("orderId", orderId);
			opmEntity = query.list();
			for (int i = 0; i < opmEntity.size(); i++) {
				orderProductMap.add(new OrderProductMapDTO(opmEntity.get(i).getOrderId(),
						opmEntity.get(i).getProductId(), opmEntity.get(i).getProductUIN(),
						opmEntity.get(i).getProductStatus(), opmEntity.get(i).getGiftStatus()));
			}
			logger.info(InfoConstants.OrderProductMapRetrieved);
		} catch (HibernateException exp) {
			session.getTransaction().rollback();
			logger.error(ExceptionConstants.ORDER_PRODUCT_MAP_FAILURE);
			throw new SalesRepresentativeException(ExceptionConstants.ORDER_PRODUCT_MAP_FAILURE + exp.getMessage());
		} finally {
			session.close();
		}
		return orderProductMap;
	}

	/*******************************************************************************************************
	 * - Function Name : checkDispatchStatus - Input Parameters : String orderId -
	 * Return Type :List<OrderReturnEntity> - Throws :SalesRepresentativeException,
	 * ConnectException - Author : CAPGEMINI - Creation Date : 23/09/2019 -
	 * Description : checking whether the order is at all despatched
	 ********************************************************************************************************/

	@Override
	public boolean checkDispatchStatus(String orderId) throws SalesRepresentativeException, ConnectException {

		boolean dispatchStatus = false;
		Session session = null;
		List<Integer> order = new ArrayList<Integer>();
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createNativeQuery(HQLQuerryMapper.CHECK_ORDER_DISPATCH_STATUS_RETURN);
			query.setParameter("orderID", orderId);
			order = (List<Integer>) query.list();
			logger.info(InfoConstants.DispatchStatusRetrieved);

		} catch (HibernateException exp) {
			session.getTransaction().rollback();
			logger.error(ExceptionConstants.DISPATCH_STATUS_ERROR);
			throw new SalesRepresentativeException(ExceptionConstants.DISPATCH_STATUS_ERROR + exp.getMessage());
		} finally {
			session.close();

		}
		return order.get(0) == 1 ? true : false;
	}

	/*******************************************************************************************************
	 * - Function Name : getCountProduct - Input Parameters : String orderId,String
	 * productId - Return Type : int - Throws :SalesRepresentativeException,
	 * ConnectException - Author : CAPGEMINI - Creation Date : 23/09/2019 -
	 * Description : getting the count of the products ordered against a particular
	 * order
	 ********************************************************************************************************/

	@SuppressWarnings("unchecked")
	@Override
	public int getCountProduct(String orderId, String productId) throws SalesRepresentativeException, ConnectException {

		long count = 0;
		Session session = null;
		SessionFactory sessionFactory = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(OrderProductMapDTO.class);
			Criterion criterion = Restrictions.eq("orderId", orderId);
			Criterion criterion1 = Restrictions.eq("productId", productId);
			Criterion criterion2 = Restrictions.and(criterion, criterion1);
			criteria.add(criterion2);
			criteria.setProjection(Projections.rowCount());
			List employees = criteria.list();
			if (employees != null) {
				count = (long) employees.get(0);
			}
			session.getTransaction().commit();
			logger.info(InfoConstants.CountProduct);


		} catch (HibernateException exp) {
			session.getTransaction().rollback();
			logger.error(ExceptionConstants.COUNT_PRODUCT_FAILURE);
			throw new SalesRepresentativeException(ExceptionConstants.COUNT_PRODUCT_FAILURE + exp.getMessage());
		} finally {
			session.close();

		}
		return (int) count;
	}

	/*******************************************************************************************************
	 * - Function Name : updateOrderProductMapByQty - Input Parameters : String
	 * orderId, String productId, int qty - Return Type :boolean - Throws
	 * SalesRepresentativeException - Author : CAPGEMINI - Creation Date :
	 * 23/09/2019 - Description : Updates the respective product status in the order
	 * product map
	 * 
	 * @throws SalesRepresentativeException
	 * @throws ConnectException
	 ********************************************************************************************************/
	@Override
	public boolean updateOrderProductMapByQty(String orderId, String productId, int qty)
			throws SalesRepresentativeException, ConnectException {
		boolean updateStatus = false;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createNativeQuery(HQLQuerryMapper.UPDATE_ORDER_PRODUCT_MAP_BY_QTY);
			query.setParameter("orderId", orderId);
			query.setParameter("productId", productId);
			query.setParameter("qty", qty);
			int result = query.executeUpdate();
			updateStatus = result > 0 ? true : false;
			session.getTransaction().commit();
			logger.info(InfoConstants.OrderProductMapUpdatedByQty);
		} catch (HibernateException exp) {
			session.getTransaction().rollback();
			logger.error(ExceptionConstants.COUNT_PRODUCT_FAILURE);
			throw new SalesRepresentativeException(ExceptionConstants.COUNT_PRODUCT_FAILURE + exp.getMessage());
		} finally {
			session.close();

		}
		return updateStatus;
	}

	/*******************************************************************************************************
	 * - Function Name : updateOrderReturn - Input Parameters : String orderId, -
	 * String productID, int qty ,String reason,String userId - Return Type :boolean
	 * - Throws :SalesRepresentativeException - Author : CAPGEMINI - Creation Date :
	 * 23/09/2019 - Description : Upload the respective products in the orderReturn
	 * Table
	 * 
	 * @throws SalesRepresentativeException
	 * @throws ConnectException
	 ********************************************************************************************************/
	@Override
	public boolean updateOrderReturn(String orderId, String productId, String userId, String reason, int qty)
			throws SalesRepresentativeException, ConnectException {
		boolean orderReturnStatus = false;
		Session session = null;
		Session session2 = null;
		List<String> productUIN = new ArrayList<String>();
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		SalesRepresentativeDao salesRepDao = new SalesRepresentativeDaoImpl();
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();

			OrderReturnDTO orderReturn = new OrderReturnDTO();
			Query query = session.createNativeQuery(HQLQuerryMapper.GET_PRODUCT_UIN);
			query.setParameter("orderId", orderId);
			query.setParameter("productId", productId);
			query.setParameter("qty", qty);
			productUIN = (List<String>) query.list();
			for (int i = 0; i < productUIN.size(); i++) {
				session2 = getSessionFactory().openSession();
				session2.beginTransaction();
				orderReturn.setOrderId(orderId);
				orderReturn.setProductId(productId);
				orderReturn.setProductUIN(productUIN.get(i));
				orderReturn.setOrderReturnReason(reason);
				orderReturn.setUserId(userId);
				orderReturn.setOrderReturnTime(sqlDate);
				session2.save(orderReturn);
				session2.getTransaction().commit();
				orderReturnStatus = true;
			}
			logger.info(InfoConstants.OrderReturnUpdated);


		} catch (HibernateException exp) {
			session.getTransaction().rollback();
			logger.error(ExceptionConstants.COUNT_PRODUCT_FAILURE);
			throw new SalesRepresentativeException(ExceptionConstants.COUNT_PRODUCT_FAILURE + exp.getMessage());
		} finally {
			session.close();
			session2.close();
		}
		return orderReturnStatus;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getOrderDetails(String orderId) - Input Parameters :
	 * orderId - Return Type : String - Throws : Exception - Author : CAPGEMINI -
	 * Creation Date : 28/09/2019 - Description : Checking if orderId exists and
	 * also getting order details
	 ********************************************************************************************************/
	@Override
	public String getOrderDetails(String orderId) throws Exception {
		String orderID = null;
		Session session = null;
		try {
			// exceptionProps = PropertiesLoader.loadProperties(EXCEPTION_PROPERTIES_FILE);
			// goProps = PropertiesLoader.loadProperties(GO_PROPERTIES_FILE);
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Criteria query = session.createCriteria(OrderDTO.class);
			query.setProjection(Projections.property("orderId"));
			query.add(Restrictions.eq("orderId", orderId));
			List<String> orderList = (List<String>) query.list();

			orderID = orderList.get(0).toString();
			if (orderID != null) {
				return orderID;
			}
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			// GoLog.logger.error(exceptionProps.getProperty("orderId_not_found_failure"));
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				throw new SalesRepresentativeException(e.getMessage());
				// throw new ConnectException(Constants.connectionError);
			}
		}
		return orderID;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : checkSalesRepId(String userId) - Input Parameters : userId
	 * - Return Type : boolean - Throws : Exception - Author : CAPGEMINI - Creation
	 * Date : 28/09/2019 - Description : Checking if userId exists
	 ********************************************************************************************************/
	@Override
	public boolean checkSalesRepId(String userId) throws Exception {
		boolean checkSalesRepIdFlag = false;
		Session session = null;
		String userID = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery(HQLQuerryMapper.IS_SALES_REP_ID_PRESENT);
			query.setParameter("userID", userId);
			List<SalesRepDTO> userList = (List<SalesRepDTO>) query.list();
			if (userList.size()!=0) {
				userID = userList.get(0).getUserId().toString();
			}
			if (userID != null) {
				checkSalesRepIdFlag = true;
				return checkSalesRepIdFlag;
			}
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			// GoLog.logger.error(exceptionProps.getProperty("userId_not_found_failure"));
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new SalesRepresentativeException(e.getMessage());
			}
		}
		return checkSalesRepIdFlag;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : checkDispatchStatusForCancelling(String orderId) - Input
	 * Parameters : orderId - Return Type : boolean - Throws : Exception - Author :
	 * CAPGEMINI - Creation Date : 28/09/2019 - Description : Check if order is
	 * dispatched
	 ********************************************************************************************************/
	@Override
	public boolean checkDispatchStatusForCancelling(String orderId) throws Exception {
		Session session = null;
		boolean checkDispatchStatusFlag = false;
		byte index = 0;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery(HQLQuerryMapper.CHECK_ORDER_DISPATCH_STATUS);
			query.setParameter("orderID", orderId);
			List<Byte> orderDipatchStatusList = (List<Byte>) query.list();
			index = Byte.parseByte(orderDipatchStatusList.get(0).toString());
			if (index == 1) {
				checkDispatchStatusFlag = true;
			}
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			// GoLog.logger.error(exceptionProps.getProperty("productId_not_found_failure"));
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		return checkDispatchStatusFlag;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getOrderProductMapForCancelling(String orderId) - Input
	 * Parameters : orderId - Return Type : List<OrderReturnEntity> - Throws :
	 * Exception - Author : CAPGEMINI - Creation Date : 28/09/2019 - Description :
	 * To get a list of type OrderReturnEntity
	 ********************************************************************************************************/
	@Override
	public List<OrderProductMapDTO> getOrderProductMapForCancelling(String orderId) throws Exception {
		Session session = null;
		OrderProductMapDTO opm = null;
		List<OrderProductMapDTO> list = null;
		list = new ArrayList<OrderProductMapDTO>();
		int index = 0;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery(HQLQuerryMapper.GET_PRODUCT_MAP);
			query.setParameter("orderID", orderId);
			List<OrderProductMapDTO> orderProductMapEntityList = (List<OrderProductMapDTO>) query.list();
			while (orderProductMapEntityList.size() > index) {
				int productStatus = orderProductMapEntityList.get(index).getProductStatus();
				if (productStatus != 1) {
					// GoLog.logger.error(exceptionProps.getProperty("order_return_failure"));
					throw new SalesRepresentativeException(
							"product cancel failure");/* exceptionProps.getProperty("product_cancel_failure") */
				} else {
					String productId = orderProductMapEntityList.get(index).getProductId();
					String productUIN = orderProductMapEntityList.get(index).getProductUIN();
					opm = new OrderProductMapDTO(orderId, productId, productUIN, (productStatus == 1 ? 0 : 1), 0);
					list.add(opm);
				}
				index++;
			}
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			// GoLog.logger.error(exceptionProps.getProperty("orderId_not_found_failure"));
		} finally {
			try {
				session.close();
			} catch (Exception exp) {
				logger.error(exp.getMessage());
				throw new Exception(exp.getMessage());
			}
		}
		return list;
	}

	// ------------------------ 1. GO Application --------------------------
	/**************************************************************************************************************
	 * - Function Name : cancelOrder(OrderCancelEntity orderCancel) - Input
	 * Parameters : OrderCancelEntity orderCancel - Return Type : String - Throws :
	 * Exception - Author : CAPGEMINI - Creation Date : 28/09/2019 - Description :
	 * Adding rows to OrderCancelEntity table and updating OrderReturnEntity after
	 * canceling the product
	 **************************************************************************************************************/
	@Override
	public String cancelOrder(OrderCancelDTO orderCancel) throws Exception {
		Session session = null;
		Session session2 = null;
		String cancelOrderStatus = "Order cant be cancelled";
		int value = 0;
		int i = 0;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			OrderCancelDTO oce = new OrderCancelDTO();
			oce.setOrderid(orderCancel.getOrderid());
			oce.setUserId(orderCancel.getUserId());
			oce.setProductid(orderCancel.getProductid());
			oce.setProductuin(orderCancel.getProductuin());
			oce.setOrdercanceltime(orderCancel.getOrdercanceltime());
			oce.setOrdercancelstatus(1);
			session.save(oce);
			session.getTransaction().commit();
			session2 = getSessionFactory().openSession();
			session2.beginTransaction();
			Query query = session2.createQuery(
					"update OrderProductMapDTO opm set opm.productStatus = 0 where opm.orderId =:orderID and opm.productUIN =:productUin");
			query.setParameter("orderID", orderCancel.getOrderid());
			query.setParameter("productUin", orderCancel.getProductuin());
			int rowsChanged = query.executeUpdate();
			session2.getTransaction().commit();
			System.out.println("The Order-Product-Map table's " + rowsChanged + " rows has been updated");
			cancelOrderStatus = "The product with the uin " + orderCancel.getProductuin() + " has been cancelled";
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			// GoLog.logger.error(exceptionProps.getProperty(" return_order_failure"));
		} finally {
			try {
				session.close();
				session2.close();
			} catch (Exception exp) {
				logger.error(exp.getMessage());
				throw new Exception(exp.getMessage());
			}
		}
		return cancelOrderStatus;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getProductQuantityOrdered(String orderId, String productId)
	 * - Input Parameters : orderId, productId - Return Type : int - Throws :
	 * Exception - Author : CAPGEMINI - Creation Date : 28/09/2019 - Description :
	 * Return the quantity of product ordered
	 ********************************************************************************************************/
	@Override
	public int getProductQuantityOrdered(String orderId, String productId) throws Exception {
		Session session = null;
		SessionFactory sessionFactory = null;
		int productQuantity = 0;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery(HQLQuerryMapper.GET_PRODUCT_QUANTITY);
			query.setParameter("orderID", orderId);
			query.setParameter("productID", productId);
			List<Long> prodQtyList = (List<Long>) query.list();
			productQuantity = Integer.parseInt(prodQtyList.get(0).toString());
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			throw new SalesRepresentativeException(exp.getMessage());
			// GoLog.logger.error(exceptionProps.getProperty("product_quantity_failure"));
		} finally {
			try {
				session.close();
			} catch (Exception exp) {
				logger.error(exp.getMessage());
				throw new Exception(exp.getMessage());
			}
		}
		return productQuantity;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : cancelProduct(String orderId, String productId, int
	 * productQtyOrdered, int quantity) - Input Parameters : orderId, productId,
	 * productQty, quantity - Return Type : String - Throws : Exception - Author :
	 * CAPGEMINI - Creation Date : 28/09/2019 - Description : Updating the
	 * Order_Cancel table after canceling the product
	 ********************************************************************************************************/
	@Override
	public String cancelProduct(String orderId, String productId, int productQtyOrdered, int quantity)
			throws Exception {
		Session session = null;
		String cancelProductStatus = "Product cant be cancelled";
		int rowsChanged = 0;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			if (productQtyOrdered == quantity) {
				Query query = session.createQuery(HQLQuerryMapper.UPDATE_ORDER_PRODUCT_MAP_CANCEL_PROD_EQUAL_QUANTITY);
				query.setParameter("orderID", orderId);
				query.setParameter("productID", productId);
				rowsChanged = query.executeUpdate();
				session.getTransaction().commit();
			} else if (productQtyOrdered > quantity) {
				Query query = session
						.createNativeQuery(HQLQuerryMapper.UPDATE_ORDER_PRODUCT_MAP_CANCEL_PROD_LESS_QUANTITY);
				query.setParameter("orderID", orderId);
				query.setParameter("productID", productId);
				query.setParameter("quantity", quantity);
				rowsChanged = query.executeUpdate();
				session.getTransaction().commit();
			}
			cancelProductStatus = "The given quantity of product has been cancelled and " + String.valueOf(rowsChanged)
					+ " rows has been changed";
			System.out.println(cancelProductStatus);
			return cancelProductStatus;
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			// throw new SalesRepresentativeException(e.getMessage());
			// GoLog.logger.error(exceptionProps.getProperty("product_quantity_failure"));
		} finally {
			try {
				session.close();
			} catch (Exception exp) {
				logger.error(exp.getMessage());
				throw new Exception(exp.getMessage());
			}
		}
		return cancelProductStatus;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************************
	 * - Function Name : updateOrderCancelForProduct(String orderId, String
	 * productId, int productQtyOrdered, int qty, String userId) - Input Parameters
	 * : orderId, productId, productQtyOrdered, qty, userId - Return Type : String -
	 * Throws : Exception - Author : CAPGEMINI - Creation Date : 28/09/2019 -
	 * Description : Adding rows to OrderCancelEntity table after canceling the
	 * product
	 ******************************************************************************************************************/
	@Override
	public String updateOrderCancelForProduct(String orderId, String productId, int productQtyOrdered, int quantity,
			String userId) throws Exception {
		String statusCancelOrderForProduct = null;
		Session session = null;
		Session session2 = null;
		int index = 0;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			if (productQtyOrdered == quantity) {
				Query query = session.createQuery(HQLQuerryMapper.GET_ORDER_PRODUCT_MAP_CANCEL_PROD_EQUAL_QUANTITY);
				query.setParameter("orderID", orderId);
				query.setParameter("productID", productId);
				query.setParameter("value", 0);
				List<OrderProductMapDTO> orderProductMapEntityList = (List<OrderProductMapDTO>) query.list();
				session2 = getSessionFactory().openSession();
				session2.beginTransaction();
				while (orderProductMapEntityList.size() > index) {
					OrderCancelDTO oce = new OrderCancelDTO();
					oce.setOrderid(orderProductMapEntityList.get(index).getOrderId());
					oce.setUserId(userId);
					oce.setProductid(orderProductMapEntityList.get(index).getProductId());
					oce.setProductuin(orderProductMapEntityList.get(index).getProductUIN());
					Date date = new Date();
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					oce.setOrdercanceltime(sqlDate);
					oce.setOrdercancelstatus(1);
					session2.save(oce);
					session2.getTransaction().commit();
					index++;
					session2 = getSessionFactory().openSession();
					session2.beginTransaction();
				}
				System.out.println(
						"The order-cancel table's " + orderProductMapEntityList.size() + " rows has been inserted");
			} else if (productQtyOrdered > quantity) {
				Query query = session.createQuery(HQLQuerryMapper.GET_ORDER_PRODUCT_MAP_CANCEL_PROD_LESS_QUANTITY);
				query.setParameter("orderID", orderId);
				query.setParameter("productID", productId);
				query.setFirstResult(0);
				query.setMaxResults(quantity);
				List<OrderProductMapDTO> orderProductMapEntityList = (List<OrderProductMapDTO>) query.list();
				session2 = getSessionFactory().openSession();
				session2.beginTransaction();
				while (orderProductMapEntityList.size() > index) {
					OrderCancelDTO oce = new OrderCancelDTO();
					oce.setOrderid(orderProductMapEntityList.get(index).getOrderId());
					oce.setUserId(userId);
					oce.setProductid(orderProductMapEntityList.get(index).getProductId());
					oce.setProductuin(orderProductMapEntityList.get(index).getProductUIN());
					Date date = new Date();
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					oce.setOrdercanceltime(sqlDate);
					oce.setOrdercancelstatus(1);
					session2.save(oce);
					session2.getTransaction().commit();
					index++;
					session2 = getSessionFactory().openSession();
					session2.beginTransaction();
				}
				System.out.println(
						"The order-cancel table's " + orderProductMapEntityList.size() + " rows has been inserted");
			}
			statusCancelOrderForProduct = "The given quantity of product has been cancelled";
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			throw new SalesRepresentativeException(exp.getMessage());
			// GoLog.logger.error(exceptionProps.getProperty("cancel_order_failure"));
		} finally {
			try {
				session.close();
				session2.close();
			} catch (Exception exp) {
				logger.error(exp.getMessage());
				throw new SalesRepresentativeException(exp.getMessage());
			}
		}
		return statusCancelOrderForProduct;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getTargetSales(String userId) - Input Parameters : userId -
	 * Return Type : String - Throws : Exception - Author : CAPGEMINI - Creation
	 * Date : 28/09/2019 - Description : Return Target Sales and Target Status for a
	 * Sales Representative
	 ********************************************************************************************************/
	@Override
	public String getTargetSales(String userId) throws Exception {
		Session session = null;
		String targetStatus = null;
		int targetSalesStatus = 0;
		String status = null;
		double targetSales = 0.0;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery(HQLQuerryMapper.SELECT_SALES_REP_TARGET);
			query.setParameter("userID", userId);
			List<Double> targetSalesList = (List<Double>) query.list();
			targetSales = Double.parseDouble(targetSalesList.get(0).toString());
			Query query2 = session.createQuery(HQLQuerryMapper.GET_TARGET_STATUS);
			query2.setParameter("userID", userId);
			List<Integer> targetStatusList = (List<Integer>) query2.list();
			targetSalesStatus = Integer.parseInt(targetStatusList.get(0).toString());
			if (targetSalesStatus == -1) {
				status = "exceeded";
			} else if (targetSalesStatus == 0) {
				status = "met";
			} else {
				status = "not met";
			}
			targetStatus = "Your target sales is " + String.valueOf(targetSales) + " and target status is " + status;
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			// GoLog.logger.error(exceptionProps.getProperty("sales representative not
			// found"));
			throw new Exception("Sales representative data not found");
		} finally {
			try {
				session.close();
			} catch (Exception exp) {
				logger.error(exp.getMessage());
				throw new SalesRepresentativeException(exp.getMessage());
			}
		}
		return targetStatus;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getBonus(String userId) - Input Parameters : userId -
	 * Return Type : String - Throws : Exception - Author : CAPGEMINI - Creation
	 * Date : 28/09/2019 - Description : Return Bonus offered to a Sales
	 * Representative
	 ********************************************************************************************************/
	@Override
	public String getBonus(String userId) throws Exception {
		Session session = null;
		Double bonus = 0.0;
		String bonusForSales = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery(HQLQuerryMapper.SELECT_SALES_REP_BONUS);
			query.setParameter("userID", userId);
			List<Double> bonusList = (List<Double>) query.list();
			bonus = Double.parseDouble(bonusList.get(0).toString());
			bonusForSales = "Your bonus is " + String.valueOf(bonus);
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			session.getTransaction().rollback();
			// GoLog.logger.error(exceptionProps.getProperty("sales representative not
			// found"));
			throw new Exception("Sales representative data not found");
		} finally {
			try {
				session.close();
			} catch (Exception exp) {
				logger.error(exp.getMessage());
				throw new SalesRepresentativeException(exp.getMessage());
				// throw new ConnectException(Constants.connectionError);
			}
		}
		return bonusForSales;
	}

}
