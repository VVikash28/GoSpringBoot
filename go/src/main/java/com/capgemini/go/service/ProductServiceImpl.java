package com.capgemini.go.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.go.dao.ProductDao;
import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.exception.ProductException;

@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : viewAllProducts - Input Parameters : - Return Type :
	 * List<ProductDTO> - Throws : ProductException - Author : AGNIBHA CHANDRA -
	 * Creation Date : 21/9/2019 - Description : to get all the product from the
	 * database
	 ********************************************************************************************************/

	public List<ProductDTO> viewAllProducts() throws ProductException {

		return productDao.viewAllProducts();
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : addProduct - Input Parameters : <ProductDTO> product -
	 * Return Type : boolean - Throws : ProductException - Author : AGNIBHA CHANDRA
	 * - Creation Date : 21/9/2019 - Description : to add a product in product
	 * database
	 * 
	 ********************************************************************************************************/

	public boolean addProduct(ProductDTO product) throws ProductException {
		return productDao.addProduct(product);
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : editProduct - Input Parameters : <ProductDTO> product -
	 * Return Type : boolean - Throws : ProductException - Author : AGNIBHA CHANDRA
	 * - Creation Date : 21/9/2019 - Description : to edit a product in product
	 * database
	 * 
	 *******************************************************************************************************/

	@Override
	public boolean editProduct(ProductDTO product) throws ProductException {
		return productDao.editProduct(product);
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : deleteProduct - Input Parameters : String productId Return
	 * Type : boolean - Throws : ProductException - Author : AGNIBHA CHANDRA -
	 * Creation Date : 21/9/2019 - Description : to remove a product in product
	 * database
	 * 
	 ********************************************************************************************************/

	public boolean deleteProduct(String productId) throws ProductException {
		return productDao.deleteProduct(productId);
	}

}
