package com.capgemini.go.testproduct;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.exception.ProductException;
import com.capgemini.go.service.GoAdminReportsService;

import com.capgemini.go.utility.InfoConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGoAdminReports {

	@Autowired
	private GoAdminReportsService go;

	@Test
	@DisplayName("Invalid Date Exception")
	public void testDateException() {
		assertThrows(GoAdminException.class, () -> go.viewSalesReportByUserAndCategory(null, null, "ALL", 5));
	}
	@Test
	@DisplayName("Invalid Date")
	public void testDate() {
        String actualMessage = null;
        try {
            if(go.viewSalesReportByUserAndCategory(null, null, "ALL", 5).size()>0) {
            actualMessage = InfoConstants.Reports_Generated_Successfully;
            }
        } catch ( ConnectException | GoAdminException exp) {
            actualMessage = exp.getMessage();
        }
        String expectedMessage =  ExceptionConstants.ERROR_IN_VIEWING+ExceptionConstants.INVALID_DATE; 
            
        assertEquals(expectedMessage, actualMessage);
	}
	@Test
	@DisplayName("Invalid Date Exception")
	public void testDateException1() {
		assertThrows(GoAdminException.class, () -> go.viewDetailedSalesReportByProduct(null, null, 5));
	}
	@Test
	@DisplayName("Invalid Date")
	public void testDate1() {
        String actualMessage = null;
        try {
            if(go.viewDetailedSalesReportByProduct(null, null, 5).size()>0) {
            actualMessage = InfoConstants.Reports_Generated_Successfully;
            }
        } catch ( ConnectException | GoAdminException exp) {
            actualMessage = exp.getMessage();
        }
        String expectedMessage =  ExceptionConstants.ERROR_IN_VIEWING+ExceptionConstants.INVALID_DATE; 
            
        assertEquals(expectedMessage, actualMessage);
	}
//	@Test
//	@DisplayName("Empty Database")
//	public void testEmptyDatabase() {
//        String actualMessage = null;
//        try {
//        	
//        	String date1="01-01-2018";
//        	String date2="01-10-2018";
//        	
//        	Date dentry = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
//			Date dexit = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
//			System.out.println(dentry+"=="+dexit);
//            if(go.viewDetailedSalesReportByProduct(dentry,dexit,5).size()>0) {
//            actualMessage = InfoConstants.Reports_Generated_Successfully;
//            }
//        } catch ( ConnectException | GoAdminException | ParseException exp) {
//            actualMessage = exp.getMessage();
//        }
//        String expectedMessage =  ExceptionConstants.ERROR_IN_VIEWING+ExceptionConstants.EMPTY_DATABASE; 
//            
//        assertEquals(expectedMessage, actualMessage);
//	}
	
}
