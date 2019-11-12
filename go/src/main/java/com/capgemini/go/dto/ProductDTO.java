package com.capgemini.go.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class ProductDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2165469246059692902L;

	@Id
	@Column(name = "PRODUCT_ID", unique = true, nullable = false, length=20)
	private String productId;
	
	@Column(name = "PRODUCT_PRICE", unique = false, nullable = false)
	private double price;
	
	@Column(name = "PRODUCT_COLOUR", unique = false, nullable = false, length = 20)
	private String colour;
	
	@Column(name = "PRODUCT_DIMENSION", unique = false, nullable = false, length = 30)
	private String dimension;
	
	@Column(name = "PRODUCT_SPECIFICATION", unique = false, nullable = false, length = 50)
	private String specification;
	
	@Column(name = "PRODUCT_MANUFACTURER", unique = false, nullable = false, length = 20)
	private String manufacturer;
	/*********************
	 * Quantity Can only be 1,2,3,4 or 5 1 = CAMPING 2 = GOLF 3 = MOUNTAINEERING 4 =
	 * OUTDOOR 5 = PERSONAL
	 *************************/
	@Column(name = "PRODUCT_QUANTITY", unique = false, nullable = false, length = 11)
	private int quantity;
	
	@Column(name = "PRODUCT_CATEGORY", unique = false, nullable = false, length = 1)
	private int productCategory;
	
	@Column(name = "PRODUCT_NAME", unique = false, nullable = false, length = 100)
	private String productName;

	public ProductDTO()
	{
		
	}
	
	public ProductDTO(String productId, double price, String colour, String dimension, String specification,
			String manufacturer, int quantity, int productCategory, String productName) {
		super();
		this.productId = productId;
		this.price = price;
		this.colour = colour;
		this.dimension = dimension;
		this.specification = specification;
		this.manufacturer = manufacturer;
		this.quantity = quantity;
		this.productCategory = productCategory;
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setProductCategory(int productCategory) {
		this.productCategory = productCategory;
	}

	public int getProductCategory() {
		return productCategory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return "ProductDTO [productId=" + productId + ", price=" + price + ", colour=" + colour + ", manufacturer="
				+ manufacturer + ", productName=" + productName + "]";
	}
	
}
