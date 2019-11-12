package com.capgemini.go.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class WhishlistIdDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3429807264653578490L;

	/**
	 * 
	 */


	@Column(name = "USER_ID",  nullable = false)
	private String userId;
	
	@Column(name = "PRODUCT_ID", nullable = false)
	private String productId;

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

	public WhishlistIdDTO(String userId, String productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}
	
	
}
