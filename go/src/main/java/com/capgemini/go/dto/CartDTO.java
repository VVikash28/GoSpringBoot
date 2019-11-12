package com.capgemini.go.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CART_ITEM")
public class CartDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RETAILER_ID", unique = false, nullable = false)
	private String userId;

	@Id
	@Column(name = "PRODUCT_ID", unique = false, nullable = false)
	private String productId;

	@Column(name = "QUANTITY", nullable = false)
	private int quantity;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public CartDTO() {
		
	}
	public CartDTO(String userId, String productId, int quantity) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartDTO [userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + "]";
	}

}