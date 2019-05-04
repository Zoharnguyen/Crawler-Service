package com.zohar.crawler.dto;

import org.bson.types.ObjectId;

public class ProductReviewRedisDto {

	private String productReviewLink;
	private ObjectId id;

	public ProductReviewRedisDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductReviewRedisDto(String productReviewLink, ObjectId id) {
		super();
		this.productReviewLink = productReviewLink;
		this.id = id;
	}

	public String getProductReviewLink() {
		return productReviewLink;
	}

	public void setProductReviewLink(String productReviewLink) {
		this.productReviewLink = productReviewLink;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}
