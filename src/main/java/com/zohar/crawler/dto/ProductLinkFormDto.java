package com.zohar.crawler.dto;

public class ProductLinkFormDto {

	private String divParent = "";
	private String linkProduct = "";
	private String nextPage = "";

	public ProductLinkFormDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductLinkFormDto(String divParent, String linkProduct, String nextPage) {
		super();
		this.divParent = divParent;
		this.linkProduct = linkProduct;
		this.nextPage = nextPage;
	}

	public String getDivParent() {
		return divParent;
	}

	public void setDivParent(String divParent) {
		this.divParent = divParent;
	}

	public String getLinkProduct() {
		return linkProduct;
	}

	public void setLinkProduct(String linkProduct) {
		this.linkProduct = linkProduct;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

}
