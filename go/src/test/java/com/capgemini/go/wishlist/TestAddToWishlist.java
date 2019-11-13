package com.capgemini.go.wishlist;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.dto.WhishlistIdDTO;
import com.capgemini.go.dto.WishlistDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.ProductException;
import com.capgemini.go.exception.WishlistException;
import com.capgemini.go.service.WishlistService;
import com.capgemini.go.utility.InfoConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAddToWishlist {

	@Autowired
	private WishlistService wishlistService;

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
	@DisplayName("Product Added Succesfully")
	@Rollback(true)
	public void testAddProductSuccess() {
		WhishlistIdDTO wish = new WhishlistIdDTO("RT03", "prod06");
		WishlistDTO newProduct = new WishlistDTO(wish);
		String actualMessage = null;
		try {

			if (wishlistService.addProductToWishlist(newProduct)) {
				actualMessage = InfoConstants.Product_Added_Success;
			}
		} catch (WishlistException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = InfoConstants.Product_Added_Success;

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Product Addition Failure")
	@Rollback(true)
	public void testAddProductFailure() {
		WhishlistIdDTO wish = new WhishlistIdDTO("RT03", "prod06");
		WishlistDTO newProduct = new WishlistDTO(wish);
		String actualMessage = null;
		try {
			if (wishlistService.addProductToWishlist(newProduct)) {
				actualMessage = InfoConstants.Product_Added_Success;
			}
		} catch (WishlistException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = ExceptionConstants.PRODUCT_ADD_ERROR + ExceptionConstants.PRODUCT_IN_WISHLIST;

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Product Addition Failure")
	@Rollback(true)
	public void testAddProductFailure1() {
		WhishlistIdDTO wish = new WhishlistIdDTO("KL01", "prod06");
		WishlistDTO newProduct = new WishlistDTO(wish);
		String actualMessage = null;
		try {
			if (wishlistService.addProductToWishlist(newProduct)) {
				actualMessage = InfoConstants.Product_Added_Success;
			}
		} catch (WishlistException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = ExceptionConstants.PRODUCT_ADD_ERROR + ExceptionConstants.PRODUCT_IN_WISHLIST;

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@DisplayName("Product Addition Failure")
	@Rollback(true)
	public void testAddProductFailure2() {
		WhishlistIdDTO wish = new WhishlistIdDTO("RT03", "1234");
		WishlistDTO newProduct = new WishlistDTO(wish);
		String actualMessage = null;
		try {
			if (wishlistService.addProductToWishlist(newProduct)) {
				actualMessage = InfoConstants.Product_Added_Success;
			}
		} catch (WishlistException exp) {
			actualMessage = exp.getMessage();
		}
		String expectedMessage = ExceptionConstants.PRODUCT_ADD_ERROR + ExceptionConstants.PRODUCT_IN_WISHLIST;

		assertEquals(expectedMessage, actualMessage);
	}

}
