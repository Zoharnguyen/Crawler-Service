package com.zohar.crawler.dto;

public class ProductInfoFormDto {

	private String linkReview = "";
	private String productName = "";
	private String productImage = "";
	private String productImageAttri = "";
	private String productRating = "";
	private String productPrice = "";
	private String keyWord = "";

	public ProductInfoFormDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductInfoFormDto(String linkReview, String productName, String productImage, String productImageAttri,
			String productRating, String productPrice, String keyWord) {
		super();
		this.linkReview = linkReview;
		this.productName = productName;
		this.productImage = productImage;
		this.productImageAttri = productImageAttri;
		this.productRating = productRating;
		this.productPrice = productPrice;
		this.keyWord = keyWord;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductImageAttri() {
		return productImageAttri;
	}

	public void setProductImageAttri(String productImageAttri) {
		this.productImageAttri = productImageAttri;
	}

	public String getLinkReview() {
		return linkReview;
	}

	public void setLinkReview(String linkReview) {
		this.linkReview = linkReview;
	}

	public String getProductRating() {
		return productRating;
	}

	public void setProductRating(String productRating) {
		this.productRating = productRating;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

}
