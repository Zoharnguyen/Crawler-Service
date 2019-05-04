package com.zohar.crawler.repos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.zohar.crawler.dto.ProductInfoFormDto;
import com.zohar.crawler.dto.ProductLinkFormDto;
import com.zohar.crawler.dto.ProductReviewFormDto;
import com.zohar.crawler.dto.ProductReviewRedisDto;
import com.zohar.crawler.service.ServiceHelper;

import redis.clients.jedis.Jedis;

public class CommunityRedisRepos {

	Logger LOGGER = Logger.getLogger(CommunityRedisRepos.class.getName());

	public ServiceHelper serviceHelper = new ServiceHelper();

//	Get List Product link forms from redis
	public List<ProductLinkFormDto> getListProductLinkForms(Jedis jedis, Gson gson, String key) {

		List<ProductLinkFormDto> productLinkFormDtos = new ArrayList<>();
		Set<String> elementSets = jedis.smembers(key);
		List<String> elementLists = new ArrayList<String>(elementSets);
		int count = 0;
		while (count < elementLists.size()) {
			String productLinkJson = elementLists.get(count);
			ProductLinkFormDto productLinkFormDto = serviceHelper.convertJsonToProductLinkForm(productLinkJson, gson);
			productLinkFormDtos.add(productLinkFormDto);
			count++;
		}
		return productLinkFormDtos;

	}

//	Get list product info form in redis
	public List<ProductInfoFormDto> getListProductInfoForms(Jedis jedis, Gson gson, String key) {

		List<ProductInfoFormDto> productInfoFormDtos = new ArrayList<>();
		Set<String> elementSets = jedis.smembers(key);
		List<String> elementLists = new ArrayList<String>(elementSets);
		int count = 0;
		while (count < elementLists.size()) {
			String productInfoJson = elementLists.get(count);
			ProductInfoFormDto productInfoFormDto = serviceHelper.convertJsonToProductInfoForm(productInfoJson, gson);
			productInfoFormDtos.add(productInfoFormDto);
			count++;
		}
		return productInfoFormDtos;

	}

//	Get list product review form in redis
	public List<ProductReviewFormDto> getListProductReviewForms(Jedis jedis, Gson gson, String key) {

		List<ProductReviewFormDto> productReviewFormDtos = new ArrayList<>();
		Set<String> elementSets = jedis.smembers(key);
		List<String> elementLists = new ArrayList<String>(elementSets);
		int count = 0;
		while (count < elementLists.size()) {
			String productReviewJson = elementLists.get(count);
			ProductReviewFormDto productReviewFormDto = serviceHelper.convertJsonToProductReviewForm(productReviewJson,
					gson);
			productReviewFormDtos.add(productReviewFormDto);
			count++;
		}
		return productReviewFormDtos;

	}

//	Get list product review dto
	public List<ProductReviewRedisDto> getListProductReviewDto(Jedis jedis, Gson gson, String key) {

		List<ProductReviewRedisDto> productReviewDtos = new ArrayList<>();
		Set<String> elementSets = jedis.smembers(key);
		List<String> elementLists = new ArrayList<String>(elementSets);
		int count = 0;
		while (count < elementLists.size()) {
			String productReviewJson = elementLists.get(count);
			ProductReviewRedisDto productReviewFormDto = serviceHelper.convertJsonToProductReviewDto(productReviewJson,
					gson);
			productReviewDtos.add(productReviewFormDto);
			count++;
		}
		return productReviewDtos;

	}

//	add new object into key in redis
	public void addObjectForm(Jedis jedis, Gson gson, Object object, String key) {

		String json = serviceHelper.convertObjectToJson(object, gson);
		jedis.sadd(key, json);
		LOGGER.log(Level.INFO, "Add object form sucess!");

	}

}
