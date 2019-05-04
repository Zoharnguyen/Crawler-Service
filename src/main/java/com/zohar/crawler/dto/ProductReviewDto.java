package com.zohar.crawler.dto;

public class ProductReviewDto {

	private String title = "";
	private String user_name = "";
	private String avatar = "";
	private long publish_date = 0;
	private String content = "";
	private int rating = 0;
	private int helpful = 0;
	private String review_id = "";
	private String product_id;
	private String user_id = "";
	private String review_link = "";

	public ProductReviewDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductReviewDto(String title, String user_name, String avatar, long publish_date, String content,
			int rating, int helpful, String review_id, String product_id, String user_id, String review_link) {
		super();
		this.title = title;
		this.user_name = user_name;
		this.avatar = avatar;
		this.publish_date = publish_date;
		this.content = content;
		this.rating = rating;
		this.helpful = helpful;
		this.review_id = review_id;
		this.product_id = product_id;
		this.user_id = user_id;
		this.review_link = review_link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public long getCreated_at() {
		return publish_date;
	}

	public void setCreated_at(long created_at) {
		this.publish_date = created_at;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public long getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(long publish_date) {
		this.publish_date = publish_date;
	}

	public int getHelpful() {
		return helpful;
	}

	public void setHelpful(int helpful) {
		this.helpful = helpful;
	}

	public String getReview_id() {
		return review_id;
	}

	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getReview_link() {
		return review_link;
	}

	public void setReview_link(String review_link) {
		this.review_link = review_link;
	}

}
