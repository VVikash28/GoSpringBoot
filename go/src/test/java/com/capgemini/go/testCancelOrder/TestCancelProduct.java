package com.capgemini.go.testCancelOrder;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.OrderNotFoundException;
import com.capgemini.go.exception.ProductNotFoundException;
import com.capgemini.go.exception.SalesRepresentativeException;
import com.capgemini.go.service.SalesRepresentativeService;
import com.capgemini.go.utility.InfoConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCancelProduct {

	@Autowired
	private SalesRepresentativeService salesRepresentativeService;

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
	@DisplayName("Product not cancelled as userId is null")
	public void testCancelProductNullUserId() throws Exception {
		String userID = null;
		String orderID = "OR157";
		String productID = "PROD06";
		int quantity = 2;
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelProduct(orderID, userID, productID, quantity);
		} catch (SalesRepresentativeException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "Sales Representative id is invalid";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Product not cancelled as orderId is null")
	public void testCancelProductNullOrderId() throws Exception {
		String userID = "SR01";
		String orderID = null;
		String productID = "PROD01";
		int quantity = 2;
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelProduct(orderID, userID, productID, quantity);
		} catch (OrderNotFoundException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "No such order id exists";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Product not cancelled as it is dispatched")
	public void testCancelProductAlreadyDispatched() throws Exception {
		String userID = "SR01";
		String orderID = "OR234";
		String productID = "PROD01";
		int quantity = 2;
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelProduct(orderID, userID, productID, quantity);
		} catch (OrderNotFoundException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "Selected Product cant be cancelled as it is dispatched! Return the Product";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Product not cancelled as wrong product id")
	public void testCancelProductIncorrectProductId() throws Exception {
		String userID = "SR01";
		String orderID = "OR157";
		String productID = "PROD04";
		int quantity = 2;
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelProduct(orderID, userID, productID, quantity);
		} catch (ProductNotFoundException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "There are no such products in the order";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Product not cancelled as quantity is more")
	public void testCancelProductMoreQuantity() throws Exception {
		String userID = "SR01";
		String orderID = "OR157";
		String productID = "PROD01";
		int quantity = 2;
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelProduct(orderID, userID, productID, quantity);
		} catch (ProductNotFoundException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "Quantity of product cancelled cant be greater than the quantity of product ordered";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Product Succesfully Cancelled")
	public void testCancelProducts() throws Exception {
		String userID = "SR01";
		String orderID = "OR157";
		String productID = "PROD01";
		int quantity = 1;
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelProduct(orderID, userID, productID, quantity);
		} catch (Exception exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "The given products are canceled";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Product Succesfully Cancelled")
	public void testCancelProducts2() throws Exception {
		String userID = "SR01";
		String orderID = "OR157";
		String productID = "PROD01";
		int quantity = 1;
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelProduct(orderID, userID, productID, quantity);
		} catch (OrderNotFoundException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "Products are not mapped with order";
		assertEquals(expectedmessage, actualMessage);
	}

}
