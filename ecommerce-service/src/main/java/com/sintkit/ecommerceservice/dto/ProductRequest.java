package com.sintkit.ecommerceservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductRequest {

	@NotBlank
	private String productName;

	private float price;

	private String slug;

	@NotBlank
	private String productDescription;

	private String productImageDBs;

	private int productQuantity;

	protected long productCreatedDate;

	protected long productUpdateDate;


}
