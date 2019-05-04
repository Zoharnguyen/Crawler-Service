package com.zohar.crawler.service;

import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.zohar.crawler.dto.ProductInfoFormDto;
import com.zohar.crawler.dto.ProductLinkFormDto;
import com.zohar.crawler.dto.ProductReviewFormDto;
import com.zohar.crawler.dto.ProductReviewRedisDto;
import com.zohar.crawler.repos.CommunityRedisRepos;

import redis.clients.jedis.Jedis;

public class ServiceRedis {

	CommunityRedisRepos communityRedisRepos = new CommunityRedisRepos();

//	Get List product link forms
	public List<ProductLinkFormDto> getListProductLinkForms(Jedis jedis, Gson gson, String key) {

		return communityRedisRepos.getListProductLinkForms(jedis, gson, key);

	}

//	Get List product information forms
	public List<ProductInfoFormDto> getListProductInfoForms(Jedis jedis, Gson gson, String key) {

		return communityRedisRepos.getListProductInfoForms(jedis, gson, key);

	}

//	Get List product review forms
	public List<ProductReviewFormDto> getListProductReviewForms(Jedis jedis, Gson gson, String key) {

		return communityRedisRepos.getListProductReviewForms(jedis, gson, key);

	}

//	Get List product review redis dto
	public List<ProductReviewRedisDto> getListProductReviewDto(Jedis jedis, Gson gson, String key) {

		return communityRedisRepos.getListProductReviewDto(jedis, gson, key);

	}

//	add new object form
	public void addObjectForm(Jedis jedis, Gson gson, Object object, String key) {

		communityRedisRepos.addObjectForm(jedis, gson, object, key);

	}

//	get all members
	public Set<String> getAllMember(Jedis jedis, String key) {
		return jedis.smembers(key);
	}

//	pop element
	public String popElement(Jedis jedis, String key) {
		return jedis.spop(key);
	}
}
