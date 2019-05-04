package com.zohar.crawler.test.json;

import com.google.gson.Gson;
import com.zohar.crawler.dto.ProductLinkFormDto;

public class Json {

	public static void main(String[] args) {
		
		ProductLinkFormDto productLinkFormDto = new ProductLinkFormDto();
		productLinkFormDto.setDivParent("1. Zohar");
		productLinkFormDto.setLinkProduct("2. Nguyen");
		productLinkFormDto.setNextPage("3. Try hard");
		Gson gson = new Gson();
		String json = gson.toJson(productLinkFormDto);
		System.out.println("After convert object to string: " + json);
		ProductLinkFormDto productLinkFormDto2 = gson.fromJson(json, ProductLinkFormDto.class);
		System.out.println("LinkProduct is : " + productLinkFormDto2.getLinkProduct());
		
	}
	
}
