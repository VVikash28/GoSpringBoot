package com.capgemini.go.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.exception.RetailerInventoryException;

@Component
public interface RetailerInventoryDao {
	// Retailer Inventory Data Access Functions
	/*******************************************************************************************************
	 * - Function Name : getSoldItemsDetails <br>
	 * - Description : to get List of all Sold Items By retailer ID <br>
	 * 
	 * @param queryArguments (retailerId)
	 * @return List<RetailerInventoryDTO>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryDTO> getSoldItemsDetails(RetailerInventoryDTO queryArguments)
			throws RetailerInventoryException;

	/*******************************************************************************************************
	 * - Function Name : getDeliveredItemsDetails <br>
	 * - Description : to get List of all Items Delivered to retailer ID <br>
	 * 
	 * @param queryArguments (retailerId)
	 * @return List<RetailerInventoryDTO>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryDTO> getDeliveredItemsDetails(RetailerInventoryDTO queryArguments)
			throws RetailerInventoryException;
	// END OF Retailer Inventory Data Access Functions

	// Functions for Retailer Inventory Manipulation
	/*******************************************************************************************************
	 * - Function Name : getItemListByRetailer <br>
	 * - Description : to get List of all Items by retailer ID <br>
	 * 
	 * @param queryArguments (retailerId)
	 * @return List<RetailerInventoryDTO>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryDTO> getItemListByRetailer(RetailerInventoryDTO queryArguments)
			throws RetailerInventoryException;

	/*******************************************************************************************************
	 * - Function Name : getListOfRetailers <br>
	 * - Description : to get List of all Retailer ID's in database <br>
	 * 
	 * @return List<RetailerInventoryDTO>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryDTO> getListOfRetailers() throws RetailerInventoryException;

	/*******************************************************************************************************
	 * - Function Name : updateProductReceiveTimeStamp <br>
	 * - Description : to update receive time stamp of a particular product for a particular retailer <br>
	 * 
	 * @param queryArguments (retailerId, productUin, productRecieveTime)
	 * @return List<RetailerInventoryDTO>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	boolean updateProductReceiveTimeStamp(RetailerInventoryDTO queryArguments) throws RetailerInventoryException;

	/*******************************************************************************************************
	 * - Function Name : updateProductSaleTimeStamp <br>
	 * - Description : to update sale time stamp of a particular product for a particular retailer <br>
	 * 
	 * @param queryArguments (retailerId, productUin, productSaleTime)
	 * @return List<RetailerInventoryDTO>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	boolean updateProductSaleTimeStamp(RetailerInventoryDTO queryArguments) throws RetailerInventoryException;

	/*******************************************************************************************************
	 * - Function Name : insertItemInRetailerInventory <br>
	 * - Description : to insert an item into inventory <br>
	 * 
	 * @param queryArguments (retailerId, productUin, productCategory, productDispatchTime)
	 * @return List<RetailerInventoryDTO>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	boolean insertItemInRetailerInventory(RetailerInventoryDTO queryArguments) throws RetailerInventoryException;

	/*******************************************************************************************************
	 * - Function Name : deleteItemInRetailerInventory <br>
	 * - Description : to delete an item in inventory <br>
	 * 
	 * @param queryArguments (retailerId, productUin)
	 * @return List<RetailerInventoryDTO>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	boolean deleteItemInRetailerInventory(RetailerInventoryDTO queryArguments) throws RetailerInventoryException;
	// end of Functions for Retailer Inventory Manipulation
}
