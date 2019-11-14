package com.capgemini.go.orderTest;

 

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 

import org.apache.log4j.Logger;
import org.hibernate.jdbc.Expectation;
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
import com.capgemini.go.dto.CartDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.GoAdminException;
import com.capgemini.go.exception.RetailerException;
import com.capgemini.go.service.OrderAndCartService;
import com.capgemini.go.utility.InfoConstants;

 

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderAndCartTest {

 

    @Autowired
    private OrderAndCartService orderAndCartService;

 

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
    @DisplayName("item added to cart successfully")
    public void addItemToCartTest1() {
        String userId = "RT01";
        String prodId = "prod99";
        int quantity = 1;
        CartDTO cart = new CartDTO(userId, prodId, quantity);
        boolean status = false;
        String actualMessage = null;
        try {
            status = orderAndCartService.addItemToCart(cart);
            if (status == true) {
                actualMessage = "Item is added to cart successfully";
            }
        } catch (Exception exp) {
            actualMessage = exp.getMessage();
        }
        System.out.println(actualMessage);
        String expectedmessage = "addItemToCart - "+ ExceptionConstants.ITEM_ALREADY_PRESENT_IN_CART;
        System.out.println(expectedmessage);
        assertEquals(expectedmessage, actualMessage);
    }
    @Test
    @DisplayName("item added to cart successfully")
    public void addItemToCartTest2() {
        String userId = "RT01";
        String prodId = "prod10";
        int quantity = 1;
        CartDTO cart = new CartDTO(userId, prodId, quantity);
        boolean status = false;
        String actualMessage = null;
        try {
            status = orderAndCartService.addItemToCart(cart);
            if (status == true) {
                actualMessage = "Item is added to cart successfully";
            }
        } catch (Exception exp) {
            actualMessage = exp.getMessage();
        }
        System.out.println(actualMessage);
        String expectedmessage = "Item is added to cart successfully";
        System.out.println(expectedmessage);
        assertEquals(expectedmessage, actualMessage);
    }
}