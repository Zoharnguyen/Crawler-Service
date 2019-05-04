package com.zohar.crawler.dto;

public class ProductReviewFormDto {

	private String divParent = "";
	private String title = "";
	private String reviewerName = "";
	private String reviewerAvar = "";
	private String imageAttri = "";
	private String date = "";
	private String content = "";
	private String rating = "";
	private String helpFull = "";
	private String id = "";
	private String nextPage = "";

	public ProductReviewFormDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductReviewFormDto(String divParent, String title, String reviewerName, String reviewerAvar,
			String imageAttri, String date, String content, String rating, String helpFull, String id,
			String nextPage) {
		super();
		this.divParent = divParent;
		this.title = title;
		this.reviewerName = reviewerName;
		this.reviewerAvar = reviewerAvar;
		this.imageAttri = imageAttri;
		this.date = date;
		this.content = content;
		this.rating = rating;
		this.helpFull = helpFull;
		this.id = id;
		this.nextPage = nextPage;
	}

	public String getDivParent() {
		return divParent;
	}

	public void setDivParent(String divParent) {
		this.divParent = divParent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public String getReviewerAvar() {
		return reviewerAvar;
	}

	public void setReviewerAvar(String reviewerAvar) {
		this.reviewerAvar = reviewerAvar;
	}

	public String getImageAttri() {
		return imageAttri;
	}

	public void setImageAttri(String imageAttri) {
		this.imageAttri = imageAttri;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getHelpFull() {
		return helpFull;
	}

	public void setHelpFull(String helpFull) {
		this.helpFull = helpFull;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

}
