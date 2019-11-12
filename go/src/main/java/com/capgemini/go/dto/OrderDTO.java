package com.capgemini.go.dto;

 

import java.util.Date;

 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

 

@Entity
@Table(name = "`ORDER`")
public class OrderDTO {
    @Id
    @Column(name="ORDER_ID",unique = false, nullable = false)
    private String orderId;
    
    @Column(name="ORDER_DISPATCH_STATUS", unique=false, nullable= false)
    private byte orderDispatchStatus;
    
    @Temporal(TemporalType.DATE)
    @Column(name="ORDER_DISPATCH_TIME", unique=false,nullable=false)
    private Date orderDispatchTime; 
    
    @Column(name="USER_ID",unique=false,nullable=false)
    private String userId;
    
    @Column(name="ADDRESS_ID",unique=false,nullable=false)
    private String addressId;
    
    @Column(name="ORDER_INITIATE_TIME",unique=false,nullable=false)
    private Date orderInitiateTime;

 

    public OrderDTO() {
        super();
    }
    
    
    public OrderDTO(String orderId, byte orderDispatchStatus, Date orderDispatchTime, String userId,
            String addressId, Date orderInitiateTime) {
        super();
        this.orderId = orderId;
        this.orderDispatchStatus = orderDispatchStatus;
        this.orderDispatchTime = orderDispatchTime;
        this.userId = userId;
        this.addressId = addressId;
        this.orderInitiateTime = orderInitiateTime;
    }

 

    public String getOrderId() {
        return orderId;
    }

 

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

 

    public byte getOrderDispatchStatus() {
        return orderDispatchStatus;
    }

 

    public void setOrderDispatchStatus(byte orderDispatchStatus) {
        this.orderDispatchStatus = orderDispatchStatus;
    }

 

    public Date getOrderDispatchTime() {
        return orderDispatchTime;
    }

 

    public void setOrderDispatchTime(Date orderDispatchTime) {
        this.orderDispatchTime = orderDispatchTime;
    }

 

    public String getUserId() {
        return userId;
    }

 

    public void setUserId(String userId) {
        this.userId = userId;
    }

 

    public String getAddressId() {
        return addressId;
    }

 

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

 

    public Date getOrderInitiateTime() {
        return orderInitiateTime;
    }

 

    public void setOrderInitiateTime(Date orderInitiateTime) {
        this.orderInitiateTime = orderInitiateTime;
    }
}