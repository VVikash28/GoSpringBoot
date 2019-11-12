package com.capgemini.go.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductIdentityDTO implements Serializable{

	private static final long serialVersionUID = -2593500634779412169L;

	@Column(name="PRODUCT_ID" , nullable = false, length=20)
	private String productId;
	
	
	@Column(name="PRODUCT_UIN", unique = true, nullable = false, length=20)
	private String productUIN;
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductUIN() {
		return productUIN;
	}

	public void setProductUIN(String productUIN) {
		this.productUIN = productUIN;
	}
}
