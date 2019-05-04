package com.zohar.crawler.test.redis;

import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.zohar.crawler.dto.ProductReviewRedisDto;
import com.zohar.crawler.service.ServiceHelper;

import redis.clients.jedis.Jedis;

public class TestRedis {

	public static ServiceHelper serviceHelper = new ServiceHelper();	
	
	public static void main(String[] args) {
		
		Gson gson = new Gson();
		Jedis jedis = new Jedis("localhost");
		String keyProductReviews = "productReviewDto";
		ProductReviewRedisDto productReviewRedisDto = serviceHelper.convertJsonToProductReviewDto(jedis.spop(keyProductReviews), gson);
		String url = productReviewRedisDto.getProductReviewLink();
		ObjectId idObject = productReviewRedisDto.getId();
		System.out.println("Url : "+url);
		System.out.println("Id: " + idObject);
	}
	
}
