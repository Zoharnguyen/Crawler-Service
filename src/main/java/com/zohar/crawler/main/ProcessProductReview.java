package com.zohar.crawler.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zohar.crawler.dto.ProductReviewFormDto;
import com.zohar.crawler.dto.ProductReviewRedisDto;
import com.zohar.crawler.service.ServiceHelper;
import com.zohar.crawler.service.ServiceJsoup;
import com.zohar.crawler.service.ServiceRedis;

import redis.clients.jedis.Jedis;

public class ProcessProductReview {

	public static ServiceHelper serviceHelper = new ServiceHelper();
	public static ServiceJsoup serviceJsoup = new ServiceJsoup();
	public static ServiceRedis serviceRedis = new ServiceRedis();

	public static void main(String[] args) throws IOException {

//		initialize variable
		HashMap<String, String> mapCookies = serviceHelper.addCookies();
		String userAgent = "Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36";
		Jedis jedis = new Jedis();
		Gson gson = new Gson();
		String key = "productReviewForms";
		List<ProductReviewFormDto> productReviewFormDtos = serviceRedis.getListProductReviewForms(jedis, gson, key);
		Scanner sc = new Scanner(System.in);
		String keyProductReviews = "productReviewDto";
//		MongoClient mongoClient = new MongoClient("localhost", 27017);
//		MongoDatabase database = mongoClient.getDatabase("crawler_version_0-0-1");
		MongoClientURI uri = new MongoClientURI("mongodb://review:re%401view!@generitas.vn:27017/review");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("review");
		MongoCollection<org.bson.Document> collection = database.getCollection("test_product_reviews");

//		Get product review

		while (serviceRedis.getAllMember(jedis, keyProductReviews).size() > 0) {

			ProductReviewRedisDto productReviewRedisDto = serviceHelper
					.convertJsonToProductReviewDto(serviceRedis.popElement(jedis, keyProductReviews), gson);
			String url = productReviewRedisDto.getProductReviewLink();
			ObjectId idObject = productReviewRedisDto.getId();

			Document document = Jsoup.connect(url).userAgent(userAgent).cookies(mapCookies).timeout(20000).get();
			for (int i = 0; i < productReviewFormDtos.size(); i++) {
//				try {

				String cssDivParent = productReviewFormDtos.get(i).getDivParent();
				String cssQTitle = productReviewFormDtos.get(i).getTitle();
				String cssQReviewerName = productReviewFormDtos.get(i).getReviewerName();
				String cssQReviewerAvatar = productReviewFormDtos.get(i).getReviewerAvar();
				String cssImageatttribute = productReviewFormDtos.get(i).getImageAttri();
				String cssQDate = productReviewFormDtos.get(i).getDate();
				String cssQContent = productReviewFormDtos.get(i).getContent();
				String cssQRating = productReviewFormDtos.get(i).getRating();
				String cssQHelpful = productReviewFormDtos.get(i).getHelpFull();
				String cssQId = productReviewFormDtos.get(i).getId();
				String cssQNextPage = productReviewFormDtos.get(i).getNextPage();

				serviceJsoup.getProductReview(document, cssDivParent, cssQTitle, cssQReviewerName, cssQReviewerAvatar,
						cssImageatttribute, cssQDate, cssQContent, cssQRating, cssQHelpful, cssQId, cssQNextPage, url,
						collection, gson, idObject);
			}
		}
	}

//	add new form product review
	public static ProductReviewFormDto addFormAndUse(Scanner sc, Document document, String url,
			MongoCollection<org.bson.Document> collection, Gson gson, ObjectId idObject) {

		ProductReviewFormDto productReviewFormDto = null;
		try {
			System.out.println("DivParent: ");
			String cssDivParent = sc.nextLine();
			System.out.println("Title: ");
			String cssQTitle = sc.nextLine();
			System.out.println("ReviewerName: ");
			String cssQReviewerName = sc.nextLine();
			System.out.println("ReviewerAvatar: ");
			String cssQReviewerAvatar = sc.nextLine();
			System.out.println("ImageAttribute: ");
			String cssImageatttribute = sc.nextLine();
			System.out.println("Date: ");
			String cssQDate = sc.nextLine();
			System.out.println("Content: ");
			String cssQContent = sc.nextLine();
			System.out.println("Rating: ");
			String cssQRating = sc.nextLine();
			System.out.println("HelpFull: ");
			String cssQHelpful = sc.nextLine();
			System.out.println("Id: ");
			String cssQId = sc.nextLine();
			System.out.println("NextPage: ");
			String cssQNextPage = sc.nextLine();

			productReviewFormDto = new ProductReviewFormDto(cssDivParent, cssQTitle, cssQReviewerName,
					cssQReviewerAvatar, cssImageatttribute, cssQDate, cssQContent, cssQRating, cssQHelpful, cssQId,
					cssQNextPage);
			serviceJsoup.getProductReview(document, cssDivParent, cssQTitle, cssQReviewerName, cssQReviewerAvatar,
					cssImageatttribute, cssQDate, cssQContent, cssQRating, cssQHelpful, cssQId, cssQNextPage, url,
					collection, gson, idObject);
		} catch (NullPointerException e) {
			System.out.println("Incorrect form. Please fill again!");
			addFormAndUse(sc, document, url, collection, gson, idObject);
		}
		return productReviewFormDto;
	}
}
