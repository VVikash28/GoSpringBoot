package com.capgemini.go.testproduct;

 

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
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.ProductException;
import com.capgemini.go.service.ProductService;
import com.capgemini.go.utility.InfoConstants;

 

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProductServiceImpl {

 

    @Autowired
    private ProductService productService;

 

    private static Logger logger;
    
    @BeforeAll
    static void setUpBeforeClass()
    {
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
    public void testAddProductSuccess()
    {
        ProductDTO newProduct = new ProductDTO("prodxxx", 9999, "#ffffff", "X", "@@@@", "xxxMan", 1,1, "sample product");
        String actualMessage = null;
        try {
            if(productService.addProduct(newProduct)) {
            actualMessage = InfoConstants.Product_Added_Success;
            }
        } catch (ProductException exp) {
            actualMessage = exp.getMessage();
        }
        String expectedMessage = InfoConstants.Product_Added_Success; 
            
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    @DisplayName("Product Addition Failure")
    @Rollback(true)
    public void testAddProductFailure() {
        ProductDTO newProduct = new ProductDTO("prod99", 9999, "#ffffff", "X", "@@@@", "xxxMan", 1,1, "sample product");
        String actualMessage = null;
        try {
            if(productService.addProduct(newProduct)) {
            actualMessage = InfoConstants.Product_Added_Success;
            }
        } catch (ProductException exp) {
            actualMessage = exp.getMessage();
        }
        String expectedMessage = ExceptionConstants.PRODUCT_ADD_ERROR+ ExceptionConstants.PRODUCT_EXISTS; 
            
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    @DisplayName("Product Addition Null")
    @Rollback(true)
    public void testAddProductNull() {
        ProductDTO newProduct = null;
        
        assertThrows(ProductException.class,()-> productService.addProduct(newProduct));
    }
    
    @Test
    @DisplayName("Product updated successfully")
    @Rollback(true)
    public void testeditProductSuccess() {
        ProductDTO newProduct = new ProductDTO("prodxxx", 9999, "#ffffff", "X", "#####", "xxxMan", 1,1, "sample product X");
        String actualMessage = null;
        try {
            if(productService.editProduct(newProduct)) {
            actualMessage = InfoConstants.Product_Update_Success;
            }
        } catch (ProductException exp) {
            actualMessage = exp.getMessage();
        }
        String expectedMessage = InfoConstants.Product_Update_Success;   
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    @DisplayName("Product updated Failure")
    @Rollback(true)
    public void testeditProductFailure() {
        ProductDTO newProduct = new ProductDTO("xxyy", 9999, "#ffffff", "X", "#####", "xxxMan", 1,1, "sample product X");
        String actualMessage = null;
        try {
            if(productService.editProduct(newProduct)) {
            actualMessage = InfoConstants.Product_Update_Success;
            }
        } catch (ProductException exp) {
            actualMessage = exp.getMessage();
        }
        String expectedMessage = ExceptionConstants.PRODUCT_UPDATE_ERROR+ ExceptionConstants.PRODUCT_NOT_EXISTS;   
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    @DisplayName("Product Addition Null")
    @Rollback(true)
    public void testeditProductNull() {
        ProductDTO newProduct = null;
        
        assertThrows(ProductException.class,()-> productService.editProduct(newProduct));
    }
    
    @Test
    @DisplayName("Product deleted successfully")
    @Rollback(true)
    public void testdeleteProductSuccess() {
        
        String actualMessage = null;
        try {
            if(productService.deleteProduct("prodxxx")) {
            actualMessage = InfoConstants.Product_Delete_Success;
            }
        } catch (ProductException exp) {
            actualMessage = exp.getMessage();
        }
        String expectedMessage = InfoConstants.Product_Delete_Success;   
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    @DisplayName("Product deleted Failure")
    @Rollback(true)
    public void testdeletedProductFailure() {
        
        String actualMessage = null;
        try {
            if(productService.deleteProduct("xxyy")) {
            actualMessage = InfoConstants.Product_Delete_Success;
            }
        } catch (ProductException exp) {
            actualMessage = exp.getMessage();
        }
        String expectedMessage = ExceptionConstants.PRODUCT_DELETE_ERROR+ ExceptionConstants.PRODUCT_NOT_EXISTS;   
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    @DisplayName("Product Addition Null")
    @Rollback(true)
    public void testdeleteProductNull() {
 
        assertThrows(ProductException.class,()-> productService.deleteProduct(null));
    }
}
 