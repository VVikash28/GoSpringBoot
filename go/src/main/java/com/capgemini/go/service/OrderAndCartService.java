package com.capgemini.go.service;

import com.capgemini.go.dto.CartDTO;
import com.capgemini.go.dto.OrderDTO;
import com.capgemini.go.dto.OrderProductMapDTO;
import com.capgemini.go.dto.ProductUINMapDTO;
import com.capgemini.go.exception.RetailerException;

public interface OrderAndCartService {
	/*******************************************************************************************************
	 * Function Name : addItemToCart <br>
	 * Input Parameters : <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 27/9/2019 <br>
	 * Description : to add item to a cart <br>
	 ********************************************************************************************************/
	boolean addItemToCart(CartDTO cartItem) throws RetailerException;

	/*******************************************************************************************************
	 * Function Name : removeItemFromCart <br>
	 * Input Parameters : <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 27/9/2019 <br>
	 * Description : to add item to a cart <br>
	 ********************************************************************************************************/
	boolean removeItemFromCart(CartDTO cartItem) throws RetailerException;

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
	boolean updateItemQuantity(CartDTO cartItem) throws RetailerException;

	/*******************************************************************************************************
	 * Function Name : registerOrder <br>
	 * Input Parameters : OrderDTO(orderId, userId, addressId, orderInitiationTime)
	 * <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 21/9/2019 <br>
	 * Description : to register an order for items in the cart <br>
	 ********************************************************************************************************/
	boolean registerOrder(OrderDTO order) throws RetailerException;

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
	boolean insertOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) throws RetailerException;

	/*******************************************************************************************************
	 * Function Name : deleteOrderProductMapEntity <br>
	 * Input Parameters : OrderProductMapDTO(orderId, productId, productUIN) <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 21/9/2019 <br>
	 * Description : to delete an item in a particular order <br>
	 ********************************************************************************************************/
	boolean deleteOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) throws RetailerException;

	/*******************************************************************************************************
	 * Function Name : updateProductUinMap <br>
	 * Input Parameters : ProductUinMapDTO (productId, productUIN) <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 21/9/2019 <br>
	 * Description : to update present status of an unique item <br>
	 ********************************************************************************************************/
	boolean updateProductUinMap(ProductUINMapDTO ProductUinMapEntity) throws RetailerException;
}
