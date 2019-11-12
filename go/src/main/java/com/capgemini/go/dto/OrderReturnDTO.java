package com.capgemini.go.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_RETURN")
public class OrderReturnDTO implements Serializable{
	
	@Id
	@Column(name="ORDER_ID")
	private String orderId;
	
	@Id
	@Column(name="USER_ID")
	private String userId;
	
	@Id
	@Column(name="PRODUCT_ID")
	private String productId;
	
	@Id
	@Column(name="PRODUCT_UIN")
	private String productUIN;

	@Column(name="ORDER_RETURN_TIME")
	private Date orderReturnTime;
	
	@Column(name="ORDER_RETURN_REASON")
	private String orderReturnReason;
	
	@Column(name="ORDER_RETURN_STATUS")
	private int orderReturnStatus;

	public OrderReturnDTO() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

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

	public String getProductUIN() {
		return productUIN;
	}

	public void setProductUIN(String productUIN) {
		this.productUIN = productUIN;
	}

	public Date getOrderReturnTime() {
		return orderReturnTime;
	}

	public void setOrderReturnTime(Date sqlDate) {
		this.orderReturnTime = sqlDate;
	}

	public String getOrderReturnReason() {
		return orderReturnReason;
	}

	public void setOrderReturnReason(String orderReturnReason) {
		this.orderReturnReason = orderReturnReason;
	}

	public int getOrderReturnStatus() {
		return orderReturnStatus;
	}

	public void setOrderReturnStatus(int orderReturnStatus) {
		this.orderReturnStatus = orderReturnStatus;
	}

	public OrderReturnDTO(String orderId, String userId, String productId, String productUIN, Date orderReturnTime,
			String orderReturnReason, int orderReturnStatus) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.productId = productId;
		this.productUIN = productUIN;
		this.orderReturnTime = orderReturnTime;
		this.orderReturnReason = orderReturnReason;
		this.orderReturnStatus = orderReturnStatus;
	}
}