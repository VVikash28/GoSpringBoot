package com.capgemini.go.dao;

import java.net.ConnectException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.capgemini.go.dto.OrderCancelDTO;
import com.capgemini.go.dto.OrderProductMapDTO;
import com.capgemini.go.dto.OrderReturnDTO;
import com.capgemini.go.exception.SalesRepresentativeException;

@Component
public interface SalesRepresentativeDao {

	/*******************************************************************************************************
	 * - Function Name : returnOrder - Input Parameters : OrderReturnDTO - Return
	 * Type : boolean - Throws :SalesRepresentativeException, ConnectException -
	 * Author : CAPGEMINI - Creation Date : 23/09/2019 - Description : Return order
	 * adds the respective order in the order_return table in the database
	 ********************************************************************************************************/
	boolean returnOrder(OrderReturnDTO or) throws SalesRepresentativeException, ConnectException;

	/*******************************************************************************************************
	 * - Function Name : updateOrderProductMap - Input Parameters : String orderId -
	 * Return Type : boolean - Throws :SalesRepresentativeException,
	 * ConnectException - Author : CAPGEMINI - Creation Date : 23/09/2019 -
	 * Description : updating Order_Product_Map in the database by setting
	 * product_status=0 for the products that have been returned
	 ********************************************************************************************************/
	boolean updateOrderProductMap(String orderId) throws SalesRepresentativeException, ConnectException;

	/*******************************************************************************************************
	 * - Function Name : getCountProduct - Input Parameters : String orderId,String
	 * productId - Return Type : int - Throws :SalesRepresentativeException,
	 * ConnectException - Author : CAPGEMINI - Creation Date : 23/09/2019 -
	 * Description : getting the count of the products ordered against a particular
	 * order
	 ********************************************************************************************************/
	int getCountProduct(String orderId, String productId) throws SalesRepresentativeException, ConnectException;

	/*******************************************************************************************************
	 * - Function Name : getCountProduct - Function Name : getOrderProductMap -
	 * Input Parameters : String orderId - Return Type :List<ORderProductMap> -
	 * Throws :SalesRepresentativeException, ConnectException - Author : CAPGEMINI -
	 * Creation Date : 23/09/2019 - Description : getting all the products against a
	 * particular order
	 ********************************************************************************************************/
	List<OrderProductMapDTO> getOrderProductMap(String orderId) throws SalesRepresentativeException, ConnectException;

	/*******************************************************************************************************
	 * - Function Name : checkDispatchStatus - Input Parameters : String orderId -
	 * Return Type :List<OrderReturnEntity> - Throws :SalesRepresentativeException,
	 * ConnectException - Author : CAPGEMINI - Creation Date : 23/09/2019 -
	 * Description : checking whether the order is at all despatched
	 ********************************************************************************************************/
	boolean checkDispatchStatus(String orderId) throws SalesRepresentativeException, ConnectException;

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
	boolean updateOrderProductMapByQty(String orderId, String productId, int qty)
			throws SalesRepresentativeException, ConnectException;

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
	boolean updateOrderReturn(String orderId, String productId, String userId, String reason, int qty)
			throws SalesRepresentativeException, ConnectException;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getOrderDetails(String orderId) - Input Parameters :
	 * orderId - Return Type : String - Throws : Exception - Author : CAPGEMINI -
	 * Creation Date : 28/09/2019 - Description : Checking if orderId exists and
	 * also getting order details
	 ********************************************************************************************************/
	String getOrderDetails(String orderId) throws Exception;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : checkSalesRepId(String userId) - Input Parameters : userId
	 * - Return Type : boolean - Throws : Exception - Author : CAPGEMINI - Creation
	 * Date : 28/09/2019 - Description : Checking if userId exists
	 ********************************************************************************************************/
	boolean checkSalesRepId(String userId) throws Exception;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : checkDispatchStatusForCancelling(String orderId) - Input
	 * Parameters : orderId - Return Type : boolean - Throws : Exception - Author :
	 * CAPGEMINI - Creation Date : 28/09/2019 - Description : Check if order is
	 * dispatched
	 ********************************************************************************************************/
	boolean checkDispatchStatusForCancelling(String orderId) throws Exception;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getOrderProductMapForCancelling(String orderId) - Input
	 * Parameters : orderId - Return Type : List<OrderReturnEntity> - Throws :
	 * Exception - Author : CAPGEMINI - Creation Date : 28/09/2019 - Description :
	 * To get a list of type OrderReturnEntity
	 ********************************************************************************************************/
	List<OrderProductMapDTO> getOrderProductMapForCancelling(String orderId) throws Exception;

	// ------------------------ GreatOutdoor Application --------------------------
	/**************************************************************************************************************
	 * - Function Name : cancelOrder(OrderCancelEntity orderCancel) - Input
	 * Parameters : OrderCancelEntity orderCancel - Return Type : String - Throws :
	 * Exception - Author : CAPGEMINI - Creation Date : 28/09/2019 - Description :
	 * Adding rows to OrderCancelEntity table and updating OrderReturnEntity after
	 * canceling the product
	 **************************************************************************************************************/
	String cancelOrder(OrderCancelDTO oc) throws Exception;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getProductQuantityOrdered(String orderId, String productId)
	 * - Input Parameters : orderId, productId - Return Type : int - Throws :
	 * Exception - Author : CAPGEMINI - Creation Date : 28/09/2019 - Description :
	 * Return the quantity of product ordered
	 ********************************************************************************************************/
	int getProductQuantityOrdered(String orderId, String productId) throws Exception;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : cancelProduct(String orderId, String productId, int
	 * productQty, int quantity) - Input Parameters : orderId, productId,
	 * productQty, quantity - Return Type : String - Throws : Exception - Author :
	 * CAPGEMINI - Creation Date : 28/09/2019 - Description : Updating the
	 * OrderReturnEntity after canceling the product
	 ********************************************************************************************************/
	String cancelProduct(String orderId, String productId, int productQtyOrdered, int quantity) throws Exception;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************************
	 * - Function Name : updateOrderCancelForProduct(String orderId, String
	 * productId, int productQtyOrdered, int quantity, String userId) - Input
	 * Parameters : orderId, productId, productQtyOrdered, quantity, userId - Return
	 * Type : String - Throws : Exception - Author : CAPGEMINI - Creation Date :
	 * 28/09/2019 - Description : Adding rows to OrderCancelEntity table after
	 * canceling the product
	 ******************************************************************************************************************/
	String updateOrderCancelForProduct(String orderId, String productId, int productQtyOrdered, int quantity,
			String userId) throws Exception;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getTargetSales(String userId) - Input Parameters : userId -
	 * Return Type : String - Throws : Exception - Author : CAPGEMINI - Creation
	 * Date : 28/09/2019 - Description : Returns Target Sales and Target Status for
	 * a Sales Representative
	 ********************************************************************************************************/
	String getTargetSales(String userId) throws Exception;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : getBonus(String userId) - Input Parameters : userId -
	 * Return Type : String - Throws : Exception - Author : CAPGEMINI - Creation
	 * Date : 28/09/2019 - Description : Returns Bonus offered to a Sales
	 * Representative
	 ********************************************************************************************************/
	String getBonus(String userId) throws Exception;

}
