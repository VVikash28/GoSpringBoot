package com.capgemini.go.service;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.go.dao.SalesRepresentativeDao;
import com.capgemini.go.dto.OrderCancelDTO;
import com.capgemini.go.dto.OrderDTO;
import com.capgemini.go.dto.OrderProductMapDTO;
import com.capgemini.go.dto.OrderReturnDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.OrderNotFoundException;
import com.capgemini.go.exception.ProductNotFoundException;
import com.capgemini.go.exception.SalesRepresentativeException;

@Service(value = "salesRepresentativeService")
public class SalesRepresentativeServiceImpl implements SalesRepresentativeService {

	private Logger logger = Logger.getRootLogger();

	@Autowired
	private SalesRepresentativeDao salesRepresentativeDao;

	public SalesRepresentativeDao getSalesRepDao() {
		return salesRepresentativeDao;
	}

	public void setSalesRepDao(SalesRepresentativeDao salesRepresentativeDao) {
		this.salesRepresentativeDao = salesRepresentativeDao;
	}

	// ------------------------ 1. GO Application --------------------------

	/*******************************************************************************************************
	 * - Function Name : returnOrder - Input Parameters : String orderId ,String
	 * userId,String reason- Return Type : - Throws :SalesRepresentativeException -
	 * Author : CAPGEMINI - Creation Date : 23/09/2019 - Description : Return order
	 * calls the dao calls checkDispatchStatus ,getOrderProductMap,and returnOrder
	 * along with updateProductMap
	 * @throws ConnectException 
	 ********************************************************************************************************/
	public boolean returnOrder(String orderId, String userId, String reason) throws SalesRepresentativeException, ConnectException {

		boolean statusOrderReturn = false;
		boolean orderProductMapStatus = false;
			if (salesRepresentativeDao.checkDispatchStatus(orderId)) {
				List<OrderProductMapDTO> opm = salesRepresentativeDao.getOrderProductMap(orderId);
				Date dt = new Date();
				for (OrderProductMapDTO index : opm) {
					OrderReturnDTO or = new OrderReturnDTO(orderId, userId, index.getProductId(), index.getProductUIN(),
							dt, reason, 1);
					statusOrderReturn = salesRepresentativeDao.returnOrder(or);
				}
				if (statusOrderReturn) {
					orderProductMapStatus = salesRepresentativeDao.updateOrderProductMap(orderId);
					orderProductMapStatus = true;
				}
			}
			if(!orderProductMapStatus) {
				throw new SalesRepresentativeException(ExceptionConstants.RETURN_ORDER_ERROR);
			}
		return orderProductMapStatus;
	}

	/*******************************************************************************************************
	 * - Function Name : returnProduct - Input Parameters : String orderId,String
	 * productId,int qty,String reason - Return Type :boolean- Throws
	 * :SalesRepresentativeException - Author : CAPGEMINI - Creation Date :
	 * 23/09/2019 - Description : checking whether the order is at all despatched
	 * @throws ConnectException 
	 ********************************************************************************************************/
	public boolean returnProduct(String orderId, String userId, String productId, int qty, String reason)
			throws SalesRepresentativeException, ConnectException {
		boolean returnProductStatus = false;
		try {
		if (salesRepresentativeDao.checkDispatchStatus(orderId)) {
				int countProd = salesRepresentativeDao.getCountProduct(orderId, productId);
				if (countProd >= qty) {
					salesRepresentativeDao.updateOrderProductMapByQty(orderId, productId, qty);
					
					salesRepresentativeDao.updateOrderReturn(orderId, productId, userId, reason, qty);
					returnProductStatus = true;
				} 
			}
		}
		catch(Exception exp) {
			throw new  SalesRepresentativeException(ExceptionConstants.RETURN_PRODUCT_ERROR);
		}
		

		return returnProductStatus;
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : cancelOrder() 
	 * - Input Parameters : String orderId, String userId 
	 * - Return Type : String
	 * - Throws : SalesRepresentativeException, OrderNotFoundException
	 * - Author : ANSHU KUMAR 
	 * - Creation Date : 23/09/2019 
	 * - Description : Cancel Order to database calls dao method getOrderDetails(orderId)
	 ********************************************************************************************************/

	public String cancelOrder(String orderId, String userId) throws Exception {

		@SuppressWarnings("unused")
		OrderDTO order = null;
		@SuppressWarnings("unused")
		OrderProductMapDTO opm = null;
		List<OrderProductMapDTO> list = null;
		String statusOrderCancel = null;
		System.out.println("Cancelling of order is being processed");
		if (salesRepresentativeDao.checkSalesRepId(userId) == false) {

			logger.error("Sales Representative id is invalid");
			throw new SalesRepresentativeException("Sales Representative id is invalid");

		} else if (salesRepresentativeDao.getOrderDetails(orderId) == null) {

			logger.error("No such order id exists");
			throw new OrderNotFoundException("No such order id exists");

		} else if ((salesRepresentativeDao.checkDispatchStatusForCancelling(orderId)) == true) {

			logger.error("Order cant be cancelled as it is dispatched! Return the order");
			throw new OrderNotFoundException("Order cant be cancelled as it is dispatched! Return the order");

		} else if (salesRepresentativeDao.getOrderProductMapForCancelling(orderId).isEmpty() == true) {

			logger.error("Products are not mapped with order");
			throw new OrderNotFoundException("Products are not mapped with order");

		} else {
			list = salesRepresentativeDao.getOrderProductMapForCancelling(orderId);
			Iterator<OrderProductMapDTO> itr = list.iterator();
			java.util.Date dt = new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(dt.getTime());
			int index = 0;
			while (itr.hasNext()) {
				OrderCancelDTO oc = new OrderCancelDTO(userId, orderId, list.get(index).getProductId(),
						list.get(index).getProductUIN(), sqlDate, 0);
				statusOrderCancel = salesRepresentativeDao.cancelOrder(oc);
				index++;
				itr.next();
			}
			System.out.println("The order-cancel table's " + index + " rows has been inserted");
			statusOrderCancel = "Order has been cancelled";
			logger.info(statusOrderCancel);
			logger.info("The order-cancel table's " + index + " rows has been inserted");
		}
		return statusOrderCancel;

	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : cancelProduct 
	 * - Input Parameters : String orderId, String userId, String productID, int qty 
	 * - Return Type : String 
	 * - Throws : SalesRepresentativeException, OrderNotFoundException, ProductNotFoundException 
	 * - Author : ANSHU KUMAR
	 * - Creation Date : 23/09/2019 
	 * - Description : Cancel Order to database calls dao method getOrderDetails(orderId)
	 ********************************************************************************************************/

	public String cancelProduct(String orderId, String userId, String productId, int quantity) throws Exception {
		@SuppressWarnings("unused")
		OrderDTO order = null;
		@SuppressWarnings("unused")
		OrderProductMapDTO opm = null;
		List<OrderProductMapDTO> list = null;
		@SuppressWarnings("unused")
		List<OrderDTO> listOrder = null;
		String statusProductCancel = null;
		System.out.println("Cancelling of Product is being processed");
		if (salesRepresentativeDao.checkSalesRepId(userId) == false) {

			logger.error("Sales Representative id is invalid");
			throw new SalesRepresentativeException("Sales Representative id is invalid");

		} else if (salesRepresentativeDao.getOrderDetails(orderId) == null) {

			logger.error("No such order id exists");
			throw new OrderNotFoundException("No such order id exists");

		} else if ((salesRepresentativeDao.checkDispatchStatusForCancelling(orderId)) == true) {

			logger.error("Selected Product cant be cancelled as it is dispatched! Return the Product");
			throw new OrderNotFoundException(
					"Selected Product cant be cancelled as it is dispatched! Return the Product");

		} else if (salesRepresentativeDao.getOrderProductMapForCancelling(orderId).isEmpty() == true) {
			logger.error("Products are not mapped with order");
			throw new OrderNotFoundException("Products are not mapped with order");

		} else {
			list = salesRepresentativeDao.getOrderProductMapForCancelling(orderId);
			@SuppressWarnings("unused")
			int totalQuantityOrdered = list.size();
			@SuppressWarnings("unused")
			String statusOrderCancelForProduct = null;
			int productQtyOrdered = salesRepresentativeDao.getProductQuantityOrdered(orderId, productId);
			if (productQtyOrdered == 0) {
				System.out.println("Invalid product id");
			}
			if (productQtyOrdered == 0) {
				logger.error("There are no such products in the order");
				throw new ProductNotFoundException("There are no such products in the order");
			} else {
				if (productQtyOrdered < quantity) {
					logger.error("Quantity of product cancelled cant be greater than the quantity of product ordered");
					throw new ProductNotFoundException(
							"Quantity of product cancelled cant be greater than the quantity of product ordered");
				} else if (productQtyOrdered == quantity) {

					statusProductCancel = salesRepresentativeDao.cancelProduct(orderId, productId, productQtyOrdered,
							quantity);
					statusOrderCancelForProduct = salesRepresentativeDao.updateOrderCancelForProduct(orderId, productId,
							productQtyOrdered, quantity, userId);
					statusProductCancel = "The given products are canceled";
				} else if (productQtyOrdered > quantity) {
					statusProductCancel = salesRepresentativeDao.cancelProduct(orderId, productId, productQtyOrdered,
							quantity);
					statusOrderCancelForProduct = salesRepresentativeDao.updateOrderCancelForProduct(orderId, productId,
							productQtyOrdered, quantity, userId);
					statusProductCancel = "The given products are canceled";
					logger.info(statusProductCancel);
					logger.info(statusOrderCancelForProduct);
				}
			}
			return statusProductCancel;
		}
	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : checkTargetSales 
	 * - Input Parameters : String userId 
	 * - Return Type : String
	 * - Throws : SalesRepresentativeException 
	 * - Author : ANSHU KUMAR 
	 * - Creation Date : 23/09/2019 
	 * - Description : Cancel Order to database calls dao method getOrderDetails(sr)
	 ********************************************************************************************************/

	public String checkTargetSales(String userId) throws Exception {

		String targetStatus = null;
		if (salesRepresentativeDao.checkSalesRepId(userId) == false) {
			throw new SalesRepresentativeException("Sales Representaive id is invalid");
		} else {
			targetStatus = salesRepresentativeDao.getTargetSales(userId);
		}

		return targetStatus;

	}

	// ------------------------ 1. GO Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : checkBonus() 
	 * - Input Parameters : String userId 
	 * - Return Type : String 
	 * - Throws : SalesRepresentativeException 
	 * - Author : ANSHU KUMAR 
	 * - Creation Date : 23/09/2019 
	 * - Description : Cancel Order to database calls dao method getOrderDetails(sr)
	 ********************************************************************************************************/

	public String checkBonus(String userId) throws Exception {
		String bonus = null;
		if (salesRepresentativeDao.checkSalesRepId(userId) == false) {
			throw new SalesRepresentativeException("Sales Representaive id is invalid");
		} else {
			bonus = salesRepresentativeDao.getBonus(userId);
		}
		return bonus;
	}

}
