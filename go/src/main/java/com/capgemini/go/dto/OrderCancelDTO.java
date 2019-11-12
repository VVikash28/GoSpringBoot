package com.capgemini.go.dto;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "OrderCancelEntity")
@Table(name = "ORDER_CANCEL", uniqueConstraints = { @UniqueConstraint(columnNames = "PRODUCT_UIN") })
public class OrderCancelDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8504444860197256592L;

	@Column(name = "ORDER_ID", unique = false, length = 20)
	private String orderid;
	
	@Column(name = "USER_ID", unique = false, length = 20)
	private String userId;

	@Column(name = "PRODUCT_ID", unique = false, length = 20)
	private String productid;
	
	@Id
	@Column(name = "PRODUCT_UIN", unique = true, length = 20)
	private String productuin;

	@Column(name = "ORDER_CANCEL_TIME", unique = false, length = 20)
	private Date ordercanceltime;

	@Column(name = "ORDER_CANCEL_STATUS", unique = false, length = 1)
	private int ordercancelstatus;

	public OrderCancelDTO() {
		super();
	}
	
	public OrderCancelDTO(String userId, String orderid, String productid, String productuin, Date ordercanceltime,
			int ordercancelstatus) {
		super();
		this.userId = userId;
		this.orderid = orderid;
		this.productid = productid;
		this.productuin = productuin;
		this.ordercanceltime = ordercanceltime;
		this.ordercancelstatus = ordercancelstatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductuin() {
		return productuin;
	}

	public void setProductuin(String productuin) {
		this.productuin = productuin;
	}

	public Date getOrdercanceltime() {
		return ordercanceltime;
	}

	public void setOrdercanceltime(Date ordercanceltime) {
		this.ordercanceltime = ordercanceltime;
	}

	public int getOrdercancelstatus() {
		return ordercancelstatus;
	}

	public void setOrdercancelstatus(int ordercancelstatus) {
		this.ordercancelstatus = ordercancelstatus;
	}

	@Override
	public String toString() {
		return "OrderCancelEntity [userId=" + userId + ", orderid=" + orderid + ", productid=" + productid
				+ ", productuin=" + productuin + ", ordercanceltime=" + ordercanceltime + ", ordercancelstatus="
				+ ordercancelstatus + "]";
	}
}
