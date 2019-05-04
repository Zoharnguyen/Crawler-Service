package com.zohar.crawler.test.temp;

public class ProductInfoFormDto {

	private String name = "";
	private String image = "";
	private String imageAttri = "";
	private String realPrice = "";
	private String salePercent = "";
	private String rating = "";
	private String countReview = "";
	private String linkReview = "";
	private String status = "";

	public ProductInfoFormDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductInfoFormDto(String name, String image, String imageAttri, String realPrice, String salePercent,
			String rating, String countReview, String linkReview, String status) {
		super();
		this.name = name;
		this.image = image;
		this.imageAttri = imageAttri;
		this.realPrice = realPrice;
		this.salePercent = salePercent;
		this.rating = rating;
		this.countReview = countReview;
		this.linkReview = linkReview;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageAttri() {
		return imageAttri;
	}

	public void setImageAttri(String imageAttri) {
		this.imageAttri = imageAttri;
	}

	public String getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}

	public String getSalePercent() {
		return salePercent;
	}

	public void setSalePercent(String salePercent) {
		this.salePercent = salePercent;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCountReview() {
		return countReview;
	}

	public void setCountReview(String countReview) {
		this.countReview = countReview;
	}

	public String getLinkReview() {
		return linkReview;
	}

	public void setLinkReview(String linkReview) {
		this.linkReview = linkReview;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
