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
import com.capgemini.go.service.SalesRepresentativeService;
import com.capgemini.go.utility.InfoConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testReturnProductServiceImplTest {

	@Autowired
	private SalesRepresentativeService salesRepresentativeService;
	
	@Test
    @DisplayName("Return Order Accepted")
    @Rollback(true)
	public void testReturnProductSuccess() {
		String actualMessage = null;
		String orderId="OR234";
		String userId="SR02";
		String reason="incomplete";
		String productID="prod02";
		int qty=2;
		try {
            if(salesRepresentativeService.returnProduct(orderId, userId, productID, qty, reason)) {
            actualMessage = InfoConstants.Return_Accepted;
            }
        } catch (Exception exp) {
            actualMessage = exp.getMessage();
        }
		String expectedMessage = InfoConstants.Return_Accepted; 
		assertEquals(expectedMessage, actualMessage);
}
	
	@Test
    @DisplayName("Return Product request failed")
    @Rollback(true)
	public void testReturnProductFailure() {
		String actualMessage = null;
		String orderId="OR234";
		String userId="SR02";
		String reason="incomplete";
		String productID="prod02";
		int qty=2;
		try {
            if(salesRepresentativeService.returnProduct(orderId, userId, productID, qty, reason)) {
            actualMessage = InfoConstants.Return_Accepted;
            }
        } catch (Exception exp) {
            actualMessage = exp.getMessage();
        }
		String expectedMessage = ExceptionConstants.RETURN_PRODUCT_ERROR; 
		assertEquals(expectedMessage, actualMessage);
}
}
