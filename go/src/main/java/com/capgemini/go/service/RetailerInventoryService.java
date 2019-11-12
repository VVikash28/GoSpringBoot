package com.capgemini.go.service;

import java.util.Calendar;
import java.util.List;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.exception.RetailerInventoryException;

public interface RetailerInventoryService {
	// Shelf Time Report and Delivery Time Report
	/*******************************************************************************************************
	 * - Function Name : getMonthlyShelfTimeReport <br>
	 * - Description : to get Monthly Shelf Time Report  <br>
	 * 
	 * @param String retailerId
	 * @param Calendar dateSelection
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getMonthlyShelfTimeReport(String retailerId, Calendar dateSelection)
			throws RetailerInventoryException;

	/*******************************************************************************************************
	 * - Function Name : getQuarterlyShelfTimeReport <br>
	 * - Description : to get Quarterly Shelf Time Report  <br>
	 * 
	 * @param String retailerId
	 * @param Calendar dateSelection
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getQuarterlyShelfTimeReport(String retailerId, Calendar dateSelection)
			throws RetailerInventoryException;

	/*******************************************************************************************************
	 * - Function Name : getYearlyShelfTimeReport <br>
	 * - Description : to get Yearly Shelf Time Report  <br>
	 * 
	 * @param String retailerId
	 * @param Calendar dateSelection
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getYearlyShelfTimeReport(String retailerId, Calendar dateSelection)
			throws RetailerInventoryException;

	/*******************************************************************************************************
	 * - Function Name : getItemWiseDeliveryTimeReport <br>
	 * - Description : to get Item wise Delivery Time Report  <br>
	 * 
	 * @param String retailerId
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getItemWiseDeliveryTimeReport(String retailerId)
			throws RetailerInventoryException;
	
	/*******************************************************************************************************
	 * - Function Name : getCategoryWiseDeliveryTimeReport <br>
	 * - Description : to get Category wise Delivery Time Report  <br>
	 * 
	 * @param String retailerId
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getCategoryWiseDeliveryTimeReport(String retailerId)
			throws RetailerInventoryException;
	
	/*******************************************************************************************************
	 * - Function Name : getOutlierCategoryItemWiseDeliveryTimeReport <br>
	 * - Description : to get Outlier Category Item wise Delivery Time Report  <br>
	 * 
	 * @param String retailerId
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getOutlierCategoryItemWiseDeliveryTimeReport(String retailerId)
			throws RetailerInventoryException;
	// end of Shelf Time Report and Delivery Time Report
	
	/*******************************************************************************************************
	 * - Function Name : getListOfRetailers <br>
	 * - Description : to get list of retailers in database  <br>
	 * - This function is called by client side application to load form data
	 * 
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getListOfRetailers() throws RetailerInventoryException;
	
	/*******************************************************************************************************
	 * - Function Name : addItemToInventory <br>
	 * - Description : to add an item to inventory  <br>
	 * - This function is to be called in the Order service when Order is placed by a retailer
	 * 
	 * @return boolean (true: if item added | false: otherwise)
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public boolean addItemToInventory (String retailerId, byte productCategory, String productId, String productUIN) throws RetailerInventoryException;
	
	/*******************************************************************************************************
	 * - Function Name : deleteItemFromInventory <br>
	 * - Description : to delete an item from inventory  <br>
	 * - This function is to be called if Order is canceled by a retailer
	 * 
	 * @return boolean (true: if item deleted | false: otherwise)
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public boolean deleteItemFromInventory (String retailerId, String productUIN) throws RetailerInventoryException;
	
	/*******************************************************************************************************
	 * - Function Name : updateItemReceiveTimestamp <br>
	 * - Description : to update receive timestamp of an item in inventory  <br>
	 * 
	 * @return boolean (true: if receive timestamp updated | false: otherwise)
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public boolean updateItemReceiveTimestamp (String retailerId, String productUIN) throws RetailerInventoryException;
	
	/*******************************************************************************************************
	 * - Function Name : updateItemSaleTimestamp <br>
	 * - Description : to update sale timestamp of an item in inventory  <br>
	 * 
	 * @return boolean (true: if sale timestamp updated | false: otherwise)
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public boolean updateItemSaleTimestamp (String retailerId, String productUIN) throws RetailerInventoryException;
	
	/*******************************************************************************************************
	 * - Function Name : getInventoryById <br>
	 * - Description : to get inventory of a particular retailer <br>
	 * 
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getInventoryById (String retailerId) throws RetailerInventoryException;
}
