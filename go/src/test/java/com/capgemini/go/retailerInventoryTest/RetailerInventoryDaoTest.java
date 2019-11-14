package com.capgemini.go.retailerInventoryTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.go.dao.RetailerInventoryDao;
import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.RetailerInventoryException;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RetailerInventoryDaoTest {

	@Autowired
	private RetailerInventoryDao retailerInventoryDao;
	
	private static Logger logger;

	@BeforeAll
	static void setUpBeforeClass() {
		logger = Logger.getRootLogger();
	}

	@BeforeEach
	void setUp() throws Exception {
		logger.info("Test Case Started");

	}

	@AfterEach
	void tearDown() throws Exception {
		logger.info("Test Case Over");
	}
	
	@Test
	@DisplayName("Get Retailer Inventory Details Test")
	public void testgetItemListByRetailer () throws Exception {
		String retailerId = "avik";
		byte productCategory = 0;
		String productId = null;
		String productUin = null;
		Calendar productDispatchTimestamp = null;
		Calendar productReceiveTimestamp = null;
		Calendar productSaleTimestamp = null;
		RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, productCategory, 
				productId, productUin, productDispatchTimestamp, productReceiveTimestamp, productSaleTimestamp);
		List<RetailerInventoryDTO> result = null;
		@SuppressWarnings("unused")
		String actualMessage = null;
		try {
			result = retailerInventoryDao.getItemListByRetailer(queryArguments);
		} catch (RetailerInventoryException exp) {
			actualMessage = exp.getMessage();
		}
		assertNotNull(result);
	}
	
	@Test
	@DisplayName("Get Retailer Inventory Details Exception Test")
	public void testgetItemListByRetailerException () throws Exception {
		String retailerId = "aik";
		byte productCategory = 0;
		String productId = null;
		String productUin = null;
		Calendar productDispatchTimestamp = null;
		Calendar productReceiveTimestamp = null;
		Calendar productSaleTimestamp = null;
		RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, productCategory, 
				productId, productUin, productDispatchTimestamp, productReceiveTimestamp, productSaleTimestamp);
		@SuppressWarnings("unused")
		List<RetailerInventoryDTO> result = null;
		
		String actualMessage = null;
		try {
			result = retailerInventoryDao.getItemListByRetailer(queryArguments);
		} catch (RetailerInventoryException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = "getItemListByRetailer - " + ExceptionConstants.NO_DATA_FOUND;
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@DisplayName("Update Receive Time Test")
	public void testupdateProductReceiveTimeStamp () throws Exception {
		String retailerId = "avik";
		byte productCategory = 2;
		String productId = "prod06";
		String productUin = "prod06999";
		Calendar productDispatchTimestamp = null;
		Calendar productReceiveTimestamp = Calendar.getInstance();
		Calendar productSaleTimestamp = null;
		RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, productCategory, 
				productId, productUin, productDispatchTimestamp, productReceiveTimestamp, productSaleTimestamp);
		boolean result = false;
		@SuppressWarnings("unused")
		String actualMessage = null;
		try {
			result = retailerInventoryDao.updateProductReceiveTimeStamp(queryArguments);
		} catch (RetailerInventoryException exp) {
			actualMessage = exp.getMessage();
		}
		assertTrue(result);
	}
	
	@Test
	@DisplayName("Update Sale Time Test")
	public void testupdateProductSaleTimeStamp () throws Exception {
		String retailerId = "avik";
		byte productCategory = 2;
		String productId = "prod06";
		String productUin = "prod06999";
		Calendar productDispatchTimestamp = null;
		Calendar productReceiveTimestamp = null;
		Calendar productSaleTimestamp = Calendar.getInstance();
		RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, productCategory, 
				productId, productUin, productDispatchTimestamp, productReceiveTimestamp, productSaleTimestamp);
		boolean result = false;
		@SuppressWarnings("unused")
		String actualMessage = null;
		try {
			result = retailerInventoryDao.updateProductSaleTimeStamp(queryArguments);
		} catch (RetailerInventoryException exp) {
			actualMessage = exp.getMessage();
		}
		assertTrue(result);
	}
}
