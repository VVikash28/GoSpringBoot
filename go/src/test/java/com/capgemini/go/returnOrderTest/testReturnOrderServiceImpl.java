package com.capgemini.go.returnOrderTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.ProductException;
import com.capgemini.go.exception.SalesRepresentativeException;
import com.capgemini.go.service.SalesRepresentativeService;
import com.capgemini.go.utility.InfoConstants;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testReturnOrderServiceImpl {

	@Autowired
	private SalesRepresentativeService salesRepresentativeService;
	
	
	@Test
    @DisplayName("Return Order Accepted")
    @Rollback(true)
	public void testReturnOrderSuccess() {
		String actualMessage = null;
		String orderId="OR789";
		String userId="SR01";
		String reason="incomplete";
		try {
            if(salesRepresentativeService.returnOrder(orderId, userId, reason)) {
            actualMessage = InfoConstants.Return_Accepted;
            }
        } catch (Exception exp) {
            actualMessage = exp.getMessage();
        }
		String expectedMessage = InfoConstants.Return_Accepted; 
		assertEquals(expectedMessage, actualMessage);
}
	@Test
    @DisplayName("Return Order request failed")
    @Rollback(true)
	public void testReturnOrderFailure() {
		String actualMessage = null;
		String orderId="OR789";
		String userId="SR01";
		String reason="incomplete";
		try {
            if(salesRepresentativeService.returnOrder(orderId, userId, reason)) {
            actualMessage = InfoConstants.Return_Accepted;
            }
        } catch (Exception exp) {
            actualMessage = exp.getMessage();
        }
		String expectedMessage = ExceptionConstants.RETURN_ORDER_ERROR; 
		assertEquals(expectedMessage, actualMessage);
}
}
