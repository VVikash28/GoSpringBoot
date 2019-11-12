package com.capgemini.go.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.go.dao.OrderAndCartDao;
import com.capgemini.go.dto.CartDTO;
import com.capgemini.go.dto.OrderDTO;
import com.capgemini.go.dto.OrderProductMapDTO;
import com.capgemini.go.dto.ProductIdentityDTO;
import com.capgemini.go.dto.ProductUINMapDTO;
import com.capgemini.go.exception.RetailerException;

@Service(value = "orderAndCartService")
public class OrderAndCartServiceImpl implements OrderAndCartService {

	@Autowired
	private OrderAndCartDao orderAndCartDao;

	public OrderAndCartDao getOrderAndCartDao() {
		return orderAndCartDao;
	}

	public void setOrderAndCartDao(OrderAndCartDao orderAndCartDao) {
		this.orderAndCartDao = orderAndCartDao;
	}

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

		CartDTO newItem = new CartDTO(cartItem.getUserId(), cartItem.getProductId(), cartItem.getQuantity());
		boolean ItemAddedToCart = orderAndCartDao.addItemToCart(newItem);
		return ItemAddedToCart;
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
		CartDTO newItem = new CartDTO(cartItem.getUserId(), cartItem.getProductId(), 0);
		
		boolean ItemRemovedFromCart = orderAndCartDao.removeItemFromCart(newItem);
		return ItemRemovedFromCart;
	}

	/*******************************************************************************************************
	 * Function Name : updateItemQuantity <br>
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
		CartDTO newItem = new CartDTO(cartItem.getUserId(), cartItem.getProductId(), cartItem.getQuantity());
		
		boolean updateItemQuantity = orderAndCartDao.updateItemQuantity(newItem);
		return updateItemQuantity;
	}

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
	@Override
	public boolean registerOrder(OrderDTO order) throws RetailerException {
		OrderDTO newOrder = new OrderDTO(order.getOrderId(), (byte) 0, null, order.getUserId(), order.getAddressId(),
				order.getOrderInitiateTime());
		
		boolean OrderRegister =  orderAndCartDao.registerOrder(newOrder);
		return OrderRegister;
	}

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

	@Override
	public boolean insertOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) throws RetailerException {
		OrderProductMapDTO orderProductMap = new OrderProductMapDTO(orderProductMapEntity.getOrderId(),
				orderProductMapEntity.getProductId(), orderProductMapEntity.getProductUIN(),
				orderProductMapEntity.getProductStatus(), orderProductMapEntity.getGiftStatus());
		
		boolean OrderProductMapInserted = orderAndCartDao.insertOrderProductMapEntity(orderProductMap);
		return OrderProductMapInserted;
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
	@Override
	public boolean deleteOrderProductMapEntity(OrderProductMapDTO orderProductMapEntity) throws RetailerException {

		OrderProductMapDTO orderProductMap = new OrderProductMapDTO(orderProductMapEntity.getOrderId(),
				orderProductMapEntity.getProductId(), orderProductMapEntity.getProductUIN(), 0, 0);
		
		boolean OrderProductMapDeleted =  orderAndCartDao.deleteOrderProductMapEntity(orderProductMap);
		return OrderProductMapDeleted;
	}

	/*******************************************************************************************************
	 * Function Name : updateProductUinMap <br>
	 * Input Parameters : ProductUinMapDTO (productId, productUIN) <br>
	 * Return Type : boolean <br>
	 * Throws : RetailerException <br>
	 * Author : Azhar <br>
	 * Creation Date : 21/9/2019 <br>
	 * Description : to update present status of an unique item <br>
	 ********************************************************************************************************/
	@Override
	public boolean updateProductUinMap(ProductUINMapDTO ProductUinMapEntity) throws RetailerException {

		String productId = ProductUinMapEntity.getProductUniqueIdentity().getProductId();
		String productUIN = ProductUinMapEntity.getProductUniqueIdentity().getProductUIN();
		ProductIdentityDTO prod = new ProductIdentityDTO();
		prod.setProductId(productId);
		prod.setProductUIN(productUIN);
		ProductUINMapDTO itemTobeUpdated = new ProductUINMapDTO(prod, false);

		boolean productUinMapupdated =  orderAndCartDao.updateProductUinMap(itemTobeUpdated);
		return productUinMapupdated;
	}

}
