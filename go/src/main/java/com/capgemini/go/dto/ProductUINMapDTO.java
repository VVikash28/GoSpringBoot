package com.capgemini.go.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "PRODUCT_UIN_MAP")
public class ProductUINMapDTO {

	private static final long serialVersionUID = -1493939388747328941L;

	@EmbeddedId
	private ProductIdentityDTO productUniqueIdentity;

	@Column(name = "PRODUCT_IS_PRESENT", nullable = false, unique = false)
	private boolean productIsPresent;

	/**
	 * @param productUniqueIdentity
	 * @param productIsPresent
	 */
	public ProductUINMapDTO()
	{
		
	}
	
	public ProductUINMapDTO(ProductIdentityDTO productUniqueIdentity, boolean productIsPresent) {
		super();
		this.productUniqueIdentity = productUniqueIdentity;
		this.productIsPresent = productIsPresent;
	}

	public ProductIdentityDTO getProductUniqueIdentity() {
		return productUniqueIdentity;
	}

	public void setProductUniqueIdentity(ProductIdentityDTO productUniqueIdentity) {
		this.productUniqueIdentity = productUniqueIdentity;
	}

	public boolean isProductIsPresent() {
		return productIsPresent;
	}

	public void setProductIsPresent(boolean productIsPresent) {
		this.productIsPresent = productIsPresent;
	}

}
