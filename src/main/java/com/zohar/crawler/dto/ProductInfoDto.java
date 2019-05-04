package com.zohar.crawler.dto;

public class ProductInfoDto {

	private String product_info_link = "";
	private String product_review_link = "";
	private String product_code = "";
	private String product_name = "";
	private String product_image = "";
	private String product_price = "";
	private double product_rating = 0.0;
	private String product_category = "";

	public ProductInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductInfoDto(String product_info_link, String product_review_link, String product_code,
			String product_name, String product_image, String product_price, double product_rating,
			String product_category) {
		super();
		this.product_info_link = product_info_link;
		this.product_review_link = product_review_link;
		this.product_code = product_code;
		this.product_name = product_name;
		this.product_image = product_image;
		this.product_price = product_price;
		this.product_rating = product_rating;
		this.product_category = product_category;
	}

	public String getProduct_category() {
		return product_category;
	}

	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}

	public String getProduct_info_link() {
		return product_info_link;
	}

	public void setProduct_info_link(String product_info_link) {
		this.product_info_link = product_info_link;
	}

	public String getProduct_review_link() {
		return product_review_link;
	}

	public void setProduct_review_link(String product_review_link) {
		this.product_review_link = product_review_link;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_image() {
		return product_image;
	}

	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}

	public String getProduct_price() {
		return product_price;
	}

	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}

	public double getProduct_rating() {
		return product_rating;
	}

	public void setProduct_rating(double product_rating) {
		this.product_rating = product_rating;
	}

}
