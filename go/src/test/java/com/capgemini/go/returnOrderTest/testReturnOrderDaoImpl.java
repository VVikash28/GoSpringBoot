package com.capgemini.go.returnOrderTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.go.dao.SalesRepresentativeDao;
import com.capgemini.go.dto.OrderProductMapDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.utility.InfoConstants;



@RunWith(SpringRunner.class)
@SpringBootTest
public class testReturnOrderDaoImpl {

	@Autowired
	private SalesRepresentativeDao salesRepresentativeDao;
	
	@Test
    @DisplayName("OrderProductMap database updated")
	@Rollback(true)
	public void testOrderProductMapUpdateSuccess() {
		String actualMessage = null;
		String orderId="OR224";
		try {
            if(salesRepresentativeDao.updateOrderProductMap(orderId)){
            actualMessage = InfoConstants.UpdatedOrderProductMap;
            }
        } catch (Exception exp) {
            actualMessage = exp.getMessage();
        }
		String expectedMessage = InfoConstants.UpdatedOrderProductMap; 
		assertEquals(expectedMessage, actualMessage);
	}
    
	@Test
	@DisplayName("OrderProductMap retrieved from the database")
	@Rollback(true)
	public void testOrderProductMapGetSuccess() {
		String actualMessage = null;
		String orderId="OR234";
		
		try {
			List<OrderProductMapDTO> opm = salesRepresentativeDao.getOrderProductMap(orderId);
			if((opm=salesRepresentativeDao.getOrderProductMap(orderId)) != null) {
				actualMessage=InfoConstants.OrderProductMapRetrieved;
			}
		}
		catch(Exception exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage=InfoConstants.OrderProductMapRetrieved;
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@DisplayName("No matching product for this order")
	@Rollback(true)
	public void testOrderProductMapGetFailure() {
		String actualMessage = null;
		String orderId="OR125";
		List<OrderProductMapDTO> opm=null;
		try {
			opm = salesRepresentativeDao.getOrderProductMap(orderId);
			if(opm.size()>0) {
				actualMessage=InfoConstants.OrderProductMapRetrieved;
			}
			else {
				actualMessage=ExceptionConstants.ORDER_PRODUCT_MAP_FAILURE;
			}
		}
		catch(Exception exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage=ExceptionConstants.ORDER_PRODUCT_MAP_FAILURE;
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@DisplayName("Dispatch status retrieved from the database")
	@Rollback(true)
	public void testcheckDispatchStatusSuccess() {
		String actualMessage = null;
		String orderId="OR789";
		try {
			if(salesRepresentativeDao.checkDispatchStatus(orderId)) {
				actualMessage=InfoConstants.DispatchStatusRetrieved;
			}
			else {
				actualMessage=ExceptionConstants.DISPATCH_STATUS_ERROR;
			}
		}
		catch(Exception exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage=InfoConstants.DispatchStatusRetrieved;
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@DisplayName("No matching order found")
	@Rollback(true)
	public void testcheckDispatchStatusFailure() {
		String actualMessage = null;
		String orderId="OR100";
		try {
			if(salesRepresentativeDao.checkDispatchStatus(orderId)) {
				actualMessage=InfoConstants.DispatchStatusRetrieved;
			}
			else {
				actualMessage=ExceptionConstants.DISPATCH_STATUS_ERROR;
			}
		}
		catch(Exception exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage=ExceptionConstants.DISPATCH_STATUS_ERROR;
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@DisplayName("Count of products against the order received")
	@Rollback(true)
	public void getCountProductSuccess() {
		String actualMessage = null;
		String orderId="OR234";
		String productId="prod02";
		int count=0;
		try {
			if((count=salesRepresentativeDao.getCountProduct(orderId,productId))>0) {
				actualMessage=InfoConstants.CountProduct;
			}
			else {
				actualMessage=ExceptionConstants.COUNT_PRODUCT_FAILURE;
			}
		}
		catch(Exception exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage=InfoConstants.CountProduct;
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@DisplayName("No product against the order")
	@Rollback(true)
	public void getCountProductFailure() {
		String actualMessage = null;
		String orderId="OR234";
		String productId="prod55";
		try {
			if(salesRepresentativeDao.getCountProduct(orderId,productId)>0) {
				actualMessage=InfoConstants.CountProduct;
			}
			else {
				actualMessage=ExceptionConstants.COUNT_PRODUCT_FAILURE;
			}
		}
		catch(Exception exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage=ExceptionConstants.COUNT_PRODUCT_FAILURE;
		assertEquals(expectedMessage, actualMessage);
	}
}
