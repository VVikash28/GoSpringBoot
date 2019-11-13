package com.capgemini.go.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.go.dao.WishlistDao;
import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.dto.WishlistDTO;
import com.capgemini.go.exception.ProductException;
import com.capgemini.go.exception.WishlistException;

@Service(value = "wishlistService")
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	private WishlistDao wishlistDao;

	public WishlistDao getWishlistDao() {
		return wishlistDao;
	}

	public void setWishlistDao(WishlistDao wishlistDao) {
		this.wishlistDao = wishlistDao;
	}

	// ------------------------ GreatOutdoor Application --------------------------
	/*******************************************************************************************************
	 * - Function Name : addProductToWishlist - Input Parameters : Product ID -
	 * Return Type : boolean - Throws : RetailerException - Author : Shalu Panwar -
	 * Creation Date : 21/9/2019 - Description : To add products to Wishlist
	 * database
	 ********************************************************************************************************/

	@Override
	public boolean addProductToWishlist(WishlistDTO wishlist) throws WishlistException {
		return wishlistDao.addProductToWishlist(wishlist);

	}
	
	/*******************************************************************************************************
	 * - Function Name : ViewWishlist - Input Parameters : Product List -
	 * Return Type : boolean - Throws : RetailerException - Author : Shalu Panwar -
	 * Creation Date : 21/9/2019 - Description : To View products in Wishlist
	 * database
	 ********************************************************************************************************/
	@Override 
	public List<ProductDTO> viewWishlist(String userId) throws WishlistException {

		return wishlistDao.viewWishlist(userId);
	}

}