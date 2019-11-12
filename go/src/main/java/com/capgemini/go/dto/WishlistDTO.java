package com.capgemini.go.dto;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "FREQUENTLY_ORDERED_LIST")
public class WishlistDTO implements Serializable {
	private static final long serialVersionUID = 2165469246059692902L;

	@EmbeddedId
	private WhishlistIdDTO id;

	public WhishlistIdDTO getId() {
		return id;
	}

	public void setId(WhishlistIdDTO id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public WishlistDTO(WhishlistIdDTO id) {
		super();
		this.id = id;
	}

}
