package com.capgemini.go.testCancelOrder;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.go.exception.OrderNotFoundException;
import com.capgemini.go.exception.SalesRepresentativeException;
import com.capgemini.go.service.SalesRepresentativeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCancelOrder {

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
	@DisplayName("Order not cancelled as userId is null")
	public void testCancelOrderNullUserId() throws Exception {
		String userID = null;
		String orderID = "OR100";
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelOrder(orderID, userID);
		} catch (SalesRepresentativeException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "Sales Representative id is invalid";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Order not cancelled as orderId is null")
	public void testCancelOrderNullOrderId() throws Exception {
		String userID = "SR01";
		String orderID = null;
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelOrder(orderID, userID);
		} catch (OrderNotFoundException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "No such order id exists";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Order not cancelled as it is dispatched")
	public void testCancelOrderAlreadyDispatched() throws Exception {
		String userID = "SR01";
		String orderID = "OR234";
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelOrder(orderID, userID);
		} catch (OrderNotFoundException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "Order cant be cancelled as it is dispatched! Return the order";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Order is successfully cancelled")
	public void testCancelOrders1() throws Exception {
		String userID = "SR01";
		String orderID = "OR100";
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelOrder(orderID, userID);
		} catch (Exception exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "Order has been cancelled";
		assertEquals(expectedmessage, actualMessage);
	}

	@Test
	@DisplayName("Order is already cancelled")
	public void testCancelOrders2() throws Exception {
		String userID = "SR01";
		String orderID = "OR100";
		String actualMessage = null;
		try {
			actualMessage = salesRepresentativeService.cancelOrder(orderID, userID);
		} catch (Exception exp) {
			actualMessage = exp.getMessage();
		}
		String expectedmessage = "Products are not mapped with order";
		assertEquals(expectedmessage, actualMessage);
	}

}
