package com.capgemini.go.service;

import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.dao.ProductDao;
import com.capgemini.go.dao.RetailerInventoryDao;
import com.capgemini.go.dao.UserDao;
import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.ProductException;
import com.capgemini.go.exception.RetailerInventoryException;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.utility.GoUtility;

@Service (value = "retailerInventoryService")
public class RetailerInventoryServiceImpl implements RetailerInventoryService {
	
	private Logger logger = Logger.getRootLogger();
	
	@Autowired
	private RetailerInventoryDao retailerInventoryDao;
	
	public RetailerInventoryDao getRetailerInventoryDao () {
		return retailerInventoryDao;
	}

	public void setRetailerInventoryDao (RetailerInventoryDao retailerInventoryDao) {
		this.retailerInventoryDao = retailerInventoryDao;
	}
	
	@Autowired
	private UserDao userDao;
	
	public UserDao getUserDao () {
		return userDao;
	}

	public void setUserDao (UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Autowired
	private ProductDao productDao;
	
	public ProductDao getProductDao () {
		return productDao;
	}

	public void setProductDao (ProductDao productDao) {
		this.productDao = productDao;
	}
	// Shelf Time Report and Delivery Time Report
	/*******************************************************************************************************
	 * - Function Name : getMonthlyShelfTimeReport <br>
	 * - Description : to get Monthly Shelf Time Report <br>
	 * 
	 * @param String   retailerId
	 * @param Calendar dateSelection
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getMonthlyShelfTimeReport(String retailerId, Calendar dateSelection)
			throws RetailerInventoryException {
		return null;
	}

	/*******************************************************************************************************
	 * - Function Name : getQuarterlyShelfTimeReport <br>
	 * - Description : to get Quarterly Shelf Time Report <br>
	 * 
	 * @param String   retailerId
	 * @param Calendar dateSelection
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getQuarterlyShelfTimeReport(String retailerId, Calendar dateSelection)
			throws RetailerInventoryException {
		return null;
	}

	/*******************************************************************************************************
	 * - Function Name : getYearlyShelfTimeReport <br>
	 * - Description : to get Yearly Shelf Time Report <br>
	 * 
	 * @param String   retailerId
	 * @param Calendar dateSelection
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getYearlyShelfTimeReport (String retailerId, Calendar dateSelection)
			throws RetailerInventoryException {
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean> ();
		
		RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, (byte)0, null, null, null, null, dateSelection);
		List<RetailerInventoryDTO> listOfSoldItems = this.retailerInventoryDao.getSoldItemsDetails(queryArguments);
		try {
			List<UserDTO> userList = this.userDao.getUserIdList();
			
			for (RetailerInventoryDTO soldItem : listOfSoldItems) {
				RetailerInventoryBean object = new RetailerInventoryBean ();
				object.setRetailerId(retailerId);
				for (UserDTO user : userList) {
					if (user.getUserId().equals(retailerId)) {
						object.setRetailerName(user.getUserName());
						break;
					}
				}
				object.setProductCategoryNumber(soldItem.getProductCategory());
				object.setProductCategoryName(GoUtility.getCategoryName(soldItem.getProductCategory()));
				object.setProductUniqueId(soldItem.getProductUniqueId());
				object.setShelfTimePeriod(GoUtility.calculatePeriod(soldItem.getProductReceiveTimestamp(), 
						soldItem.getProductSaleTimestamp()));
				object.setDeliveryTimePeriod(null);
				result.add(object);
			}
			
		} catch (UserException error) {
			logger.error(error.getMessage());
			throw new RetailerInventoryException ("getYearlyShelfTimeReport - " + ExceptionConstants.FAILED_TO_RETRIEVE_USERNAME);
		} catch (RuntimeException error) {
			logger.error(error.getMessage());
			throw new RetailerInventoryException ("getYearlyShelfTimeReport - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
		}
		return result;
	}

	/*******************************************************************************************************
	 * - Function Name : getItemWiseDeliveryTimeReport <br>
	 * - Description : to get Item wise Delivery Time Report <br>
	 * 
	 * @param String retailerId
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getItemWiseDeliveryTimeReport (String retailerId)
			throws RetailerInventoryException {
		logger.info("getItemWiseDeliveryTimeReport - " + "Request for item wise delivery time report received");
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean> ();
		
		RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, (byte)0, null, null, null, null, null);
		List<RetailerInventoryDTO> listOfDeliveredItems = this.retailerInventoryDao.getDeliveredItemsDetails(queryArguments);
				
		try {
			List<UserDTO> userList = this.userDao.getUserIdList();
			
			for (RetailerInventoryDTO deliveredItem : listOfDeliveredItems) {
				RetailerInventoryBean object = new RetailerInventoryBean ();
				object.setRetailerId(retailerId);
				for (UserDTO user : userList) {
					if (user.getUserId().equals(retailerId)) {
						object.setRetailerName(user.getUserName());
						break;
					}
				}
				object.setProductCategoryNumber(deliveredItem.getProductCategory());
				object.setProductCategoryName(GoUtility.getCategoryName(deliveredItem.getProductCategory()));
				object.setProductUniqueId(deliveredItem.getProductUniqueId());
				object.setDeliveryTimePeriod(GoUtility.calculatePeriod(deliveredItem.getProductDispatchTimestamp(), deliveredItem.getProductReceiveTimestamp()));
				object.setShelfTimePeriod(null);
				result.add(object);
			}
			
		} catch (UserException error) {
			logger.error("getItemWiseDeliveryTimeReport - " + error.getMessage());
			throw new RetailerInventoryException ("getItemWiseDeliveryTimeReport - " + ExceptionConstants.FAILED_TO_RETRIEVE_USERNAME);
		} catch (RuntimeException error) {
			logger.error("getItemWiseDeliveryTimeReport - " + error.getMessage());
			throw new RetailerInventoryException ("getItemWiseDeliveryTimeReport - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
		}
		logger.info("getItemWiseDeliveryTimeReport - " + "Sent requested data");
		return result;
	}

	/*******************************************************************************************************
	 * - Function Name : getCategoryWiseDeliveryTimeReport <br>
	 * - Description : to get Category wise Delivery Time Report <br>
	 * 
	 * @param String retailerId
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getCategoryWiseDeliveryTimeReport(String retailerId)
			throws RetailerInventoryException {
		logger.info("getCategoryWiseDeliveryTimeReport - " + "Request for Category Wise delivery time report received");
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean> ();
		
		RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, (byte)0, null, null, null, null, null);
		List<RetailerInventoryDTO> listOfDeliveredItems = this.retailerInventoryDao.getDeliveredItemsDetails(queryArguments);
		
		Map<Integer, List<RetailerInventoryBean>> map = new HashMap<Integer, List<RetailerInventoryBean>>();
		for (int category = 1; category <= 5; category++)
			map.put(category, new ArrayList<RetailerInventoryBean>());
		
		try {
			List<UserDTO> userList = this.userDao.getUserIdList();
			for (RetailerInventoryDTO deliveredItem : listOfDeliveredItems) {
				RetailerInventoryBean object = new RetailerInventoryBean ();
				object.setRetailerId(retailerId);
				for (UserDTO user : userList) {
					if (user.getUserId().equals(retailerId)) {
						object.setRetailerName(user.getUserName());
						break;
					}
				}
				object.setProductCategoryNumber(deliveredItem.getProductCategory());
				object.setProductCategoryName(GoUtility.getCategoryName(deliveredItem.getProductCategory()));
				object.setProductUniqueId(deliveredItem.getProductUniqueId());
				object.setDeliveryTimePeriod(GoUtility.calculatePeriod(deliveredItem.getProductDispatchTimestamp(), deliveredItem.getProductReceiveTimestamp()));
				object.setShelfTimePeriod(null);
				map.get(Integer.valueOf(object.getProductCategoryNumber())).add(object);
			}
			
			for (int category = 1; category <= 5; category++) {
				if (map.get(category).size() != 0) {
					int years = 0, months = 0, days = 0, count = 0;
					for (RetailerInventoryBean item : map.get(category)) {
						years += item.getDeliveryTimePeriod().getYears(); 
						months += item.getDeliveryTimePeriod().getMonths(); 
						days += item.getDeliveryTimePeriod().getDays();
						count ++;
					}
					years /= count;
					months /= count;
					days /= count;
					RetailerInventoryBean object = new RetailerInventoryBean ();
					object.setProductCategoryNumber((byte)category);
					object.setProductCategoryName(GoUtility.getCategoryName(category));
					object.setProductUniqueId("----");
					object.setDeliveryTimePeriod(Period.of(years, months, days));
					result.add(object);
				}
			}
			
		} catch (UserException error) {
			logger.error("getCategoryWiseDeliveryTimeReport - " + error.getMessage());
			error.printStackTrace();
			throw new RetailerInventoryException ("getCategoryWiseDeliveryTimeReport - " + ExceptionConstants.FAILED_TO_RETRIEVE_USERNAME);
		} catch (RuntimeException error) {
			logger.error("getCategoryWiseDeliveryTimeReport - " + error.getMessage());
			error.printStackTrace();
			throw new RetailerInventoryException ("getCategoryWiseDeliveryTimeReport - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
		}
		logger.info("getCategoryWiseDeliveryTimeReport - " + "Sent requested data");
		return result;
	}

	/*******************************************************************************************************
	 * - Function Name : get
	 * OutlierCategoryItemWiseDeliveryTimeReport <br>
	 * - Description : to get Outlier Category Item wise Delivery Time Report <br>
	 * 
	 * @param String retailerId
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getOutlierCategoryItemWiseDeliveryTimeReport(String retailerId)
			throws RetailerInventoryException {
		List<RetailerInventoryBean> categoryWiseDeliveryTime = getCategoryWiseDeliveryTimeReport(retailerId);
		int maxCatNumber = 0, minCatNumber = 0;
		for (int category = 0; category < 4; category ++) {
			if (GoUtility.comparePeriod(categoryWiseDeliveryTime.get(category).getDeliveryTimePeriod(), categoryWiseDeliveryTime.get(category+1).getDeliveryTimePeriod())) {
				
			}
		}
		return null;
	}
	// end of Shelf Time Report and Delivery Time Report

	/*******************************************************************************************************
	 * - Function Name : getListOfRetailers <br>
	 * - Description : to get list of retailers in database <br>
	 * 
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getListOfRetailers() throws RetailerInventoryException {
		logger.info("getListOfRetailers - function called");
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean> ();
		
		List<RetailerInventoryDTO> tempListOfDeliveredItems = this.retailerInventoryDao.getListOfRetailers();
		List<RetailerInventoryDTO> listOfDeliveredItems = new ArrayList<RetailerInventoryDTO> ();
		for (int index = 0; index < tempListOfDeliveredItems.size(); index++) {
			listOfDeliveredItems.add(new RetailerInventoryDTO (String.valueOf(tempListOfDeliveredItems.get(index)), 
					(byte)0, null, null, null, null, null));
		}
		logger.info("getListOfRetailers - List extracted");
		
		List<UserDTO> userList = null;
		try {
			userList = this.userDao.getUserIdList();
		} catch (UserException error) {
			logger.info("getListOfRetailers - " + error.getMessage());
			throw new RetailerInventoryException ("getListOfRetailers - " + error.getMessage());
		}
		
		for (RetailerInventoryDTO item : listOfDeliveredItems) {
			String retailerName = null;
			for (UserDTO user : userList) {
				if (user.getUserId().equals(item.getRetailerId())) {
					retailerName = user.getUserName();
					break;
				}
			}
			RetailerInventoryBean object = new RetailerInventoryBean ();
			object.setRetailerId(item.getRetailerId());
			object.setRetailerName(retailerName);
			result.add(object);
		}
		logger.info("getListOfRetailers - function return");
		return result;
	}

	/*******************************************************************************************************
	 * - Function Name : addItemToInventory <br>
	 * - Description : to add an item to inventory <br>
	 * - This function is to be called in the Order service when Order is placed by a retailer
	 * 
	 * @return boolean (true: if item added | false: otherwise)
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public boolean addItemToInventory(String retailerId, byte productCategory, String productId, String productUIN) throws RetailerInventoryException {
		logger.info("addItemToInventory - function called");
		boolean itemAdded = false;
		Calendar currentSystemTimestamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		RetailerInventoryDTO queryArgument = new RetailerInventoryDTO(retailerId, productCategory, productId, productUIN, currentSystemTimestamp, null, null);
		itemAdded = this.retailerInventoryDao.insertItemInRetailerInventory(queryArgument);
		logger.info("addItemToInventory - function return");
		return itemAdded;
	}

	/*******************************************************************************************************
	 * - Function Name : deleteItemFromInventory <br>
	 * - Description : to delete an item from inventory <br>
	 * - This function is to be called if Order is canceled by a retailer
	 * 
	 * @return boolean (true: if item deleted | false: otherwise)
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public boolean deleteItemFromInventory(String retailerId, String productUIN) throws RetailerInventoryException {
		logger.info("deleteItemFromInventory - function called");
		RetailerInventoryDTO queryArgument = new RetailerInventoryDTO(retailerId, (byte)0, null, productUIN, null, null, null);
		boolean itemDeleted = this.retailerInventoryDao.deleteItemInRetailerInventory(queryArgument);
		logger.info("deleteItemFromInventory - function return");
		return itemDeleted;
	}

	/*******************************************************************************************************
	 * - Function Name : updateItemReceiveTimestamp <br>
	 * - Description : to update receive timestamp of an item in inventory <br>
	 * 
	 * @return boolean (true: if receive timestamp updated | false: otherwise)
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public boolean updateItemReceiveTimestamp(String retailerId, String productUIN) throws RetailerInventoryException {
		logger.info("updateItemReceiveTimestamp - function called");
		Calendar currentSystemTimestamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		RetailerInventoryDTO queryArgument = new RetailerInventoryDTO(retailerId, (byte)0, null, productUIN, null, currentSystemTimestamp, null);
		boolean itemUpdated = this.retailerInventoryDao.updateProductReceiveTimeStamp(queryArgument);
		logger.info("updateItemReceiveTimestamp - function return");
		return itemUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : updateItemSaleTimestamp <br>
	 * - Description : to update sale timestamp of an item in inventory <br>
	 * 
	 * @return boolean (true: if sale timestamp updated | false: otherwise)
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public boolean updateItemSaleTimestamp(String retailerId, String productUIN) throws RetailerInventoryException {
		logger.info("updateItemSaleTimestamp - function called");
		Calendar currentSystemTimestamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		RetailerInventoryDTO queryArgument = new RetailerInventoryDTO(retailerId, (byte)0, null, productUIN, null, null, currentSystemTimestamp);
		boolean itemUpdated = this.retailerInventoryDao.updateProductSaleTimeStamp(queryArgument);
		logger.info("updateItemSaleTimestamp - function return");
		return itemUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : getInventoryById <br>
	 * - Description : to get inventory of a particular retailer <br>
	 * 
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getInventoryById(String retailerId) throws RetailerInventoryException {
		logger.info("getInventoryById - function called with argument (" + retailerId + ")");
		RetailerInventoryDTO queryArgument = new RetailerInventoryDTO(retailerId, (byte)0, null, null, null, null, null);
		List<RetailerInventoryDTO> itemList = this.retailerInventoryDao.getItemListByRetailer(queryArgument);
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean> ();
		
		try {
			UserDTO retailerDetails = this.userDao.getUserById(retailerId);
			
			List<ProductDTO> productList = this.productDao.viewAllProducts();
			
			for (RetailerInventoryDTO item : itemList) {
				RetailerInventoryBean itemBean = new RetailerInventoryBean ();
				itemBean.setRetailerId(retailerId);
				itemBean.setRetailerName(retailerDetails.getUserName());
				itemBean.setProductCategoryNumber(item.getProductCategory());
				itemBean.setProductCategoryName(GoUtility.getCategoryName(item.getProductCategory()));
				for (ProductDTO product : productList) {
					if (product.getProductId().equals(item.getProductId())) {
						itemBean.setProductName(product.getProductName());
						break;
					} else {
						continue;
					}
				}
				itemBean.setProductUniqueId(item.getProductUniqueId());
				itemBean.setShelfTimePeriod(null);
				itemBean.setDeliveryTimePeriod(null);
				result.add(itemBean);
			}
		} catch (UserException | ProductException error) {
			logger.info("getInventoryById - " + error.getMessage());
			throw new RetailerInventoryException ("getInventoryById - " + error.getMessage());
		} 
		return result;
	}
}
