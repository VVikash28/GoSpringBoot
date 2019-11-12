package com.capgemini.go.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.exception.ProductException;

@Component
public interface ProductDao {

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : viewAllProducts - Input Parameters : - Return Type :
	 * List<ProductDTO> - Throws : ProductException - Author : AGNIBHA CHANDRA -
	 * Creation Date : 21/9/2019 - Description : to get all the product from the
	 * database
	 ********************************************************************************************************/
	List<ProductDTO> viewAllProducts() throws ProductException;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : addProduct - Input Parameters : <ProductDTO> product -
	 * Return Type : boolean - Throws : ProductException - Author : AGNIBHA CHANDRA
	 * - Creation Date : 21/9/2019 - Description : to add a product in product
	 * database
	 * 
	 ********************************************************************************************************/

	boolean addProduct(ProductDTO product) throws ProductException;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : editProduct - Input Parameters : <ProductDTO> product -
	 * Return Type : boolean - Throws : ProductException - Author : AGNIBHA CHANDRA
	 * - Creation Date : 21/9/2019 - Description : to edit a product in product
	 * database
	 * 
	 ********************************************************************************************************/

	boolean editProduct(ProductDTO product) throws ProductException;

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : deleteProduct - Input Parameters : String productId Return
	 * Type : boolean - Throws : ProductException - Author : AGNIBHA CHANDRA -
	 * Creation Date : 21/9/2019 - Description : to remove a product in product
	 * database
	 * 
	 ********************************************************************************************************/

	boolean deleteProduct(String productId) throws ProductException;

}
