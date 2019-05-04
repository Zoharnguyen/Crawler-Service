package com.zohar.crawler.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zohar.crawler.dto.ProductInfoFormDto;
import com.zohar.crawler.help.ExceptionEmptyElement;
import com.zohar.crawler.service.ServiceHelper;
import com.zohar.crawler.service.ServiceJsoup;
import com.zohar.crawler.service.ServiceMongo;
import com.zohar.crawler.service.ServiceRedis;

import redis.clients.jedis.Jedis;

public class ProcessAddFormProductInfo {

	public static Logger LOGGER = Logger.getLogger(ProcessProductInfo.class.getName());

	public static ServiceHelper serviceHelper = new ServiceHelper();
	public static ServiceJsoup serviceJsoup = new ServiceJsoup();
	public static ServiceRedis serviceRedis = new ServiceRedis();
	public static ServiceMongo serviceMongo = new ServiceMongo();

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);

//		initialize variable
		HashMap<String, String> mapCookies = serviceHelper.addCookies();
		String fileName = System.getProperty("user.dir") + "\\UserAgents.txt";

		Jedis jedis = new Jedis();
		Gson gson = new Gson();
		String key = "productInfoForms";
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("crawler_version_0-0-1");
//		MongoClientURI uri = new MongoClientURI("mongodb://review:re%401view!@generitas.vn:27017/review");
//		MongoClient mongoClient = new MongoClient(uri);
//		MongoDatabase database = mongoClient.getDatabase("review");
		MongoCollection<org.bson.Document> collectionProducts = database.getCollection("products");
		MongoCollection<org.bson.Document> collectionCategories = database.getCollection("categories");
		HashMap<String, String> categoryIds = serviceMongo.getIdAndCategory(collectionCategories);

		if (jedis.exists("userAgents") == false) {
			List<String> userAgents = serviceHelper.userAgent(fileName);
			jedis.rpush("userAgents", (String[]) userAgents.toArray(new String[0]));
			System.out.println("Success");
		}

		String userAgent = jedis.lpop("userAgents");

		while (true) {

			System.out.println("Mời bạn nhập vào url: ");
			String url = sc.nextLine();
			Document document = Jsoup.parse(getHtml(url, userAgent));
			ProductInfoFormDto productInfoFormDto = addFormAndUseForm(document, sc, jedis, url, collectionProducts,
					mapCookies, gson, collectionCategories, categoryIds);
			serviceRedis.addObjectForm(jedis, gson, productInfoFormDto, key);
			System.out.println("Bạn có muốn tiếp tục thêm form mới: (yes/no)");
			String answer = sc.nextLine();
			if (!answer.equals("yes"))
				break;

		}

	}

//	add correct format product information
	public static ProductInfoFormDto addFormAndUseForm(Document document, Scanner sc, Jedis jedis, String url,
			MongoCollection<org.bson.Document> collectionProducts, HashMap<String, String> mapCookies, Gson gson,
			MongoCollection<org.bson.Document> collectionCategories, HashMap<String, String> categoryIds)
			throws IOException {

		ProductInfoFormDto productInfoFormDto = null;
		System.out.println("Product Link: " + url);
		System.out.println("CssQLinkProductReview: ");
		String cssQLinkProductReview = sc.nextLine();
		System.out.println("Name: ");
		String cssQName = sc.nextLine();
		System.out.println("Image: ");
		String cssQImage = sc.nextLine();
		System.out.println("Attribute: ");
		String attribute = sc.nextLine();
		System.out.println("Price: ");
		String cssQPrice = sc.nextLine();
		System.out.println("Rating: ");
		String cssQRating = sc.nextLine();
		System.out.println("KeyWord: ");
		String cssQKeyWord = sc.nextLine();

		productInfoFormDto = new ProductInfoFormDto(cssQLinkProductReview, cssQName, cssQImage, attribute, cssQRating,
				cssQPrice, cssQKeyWord);
		try {
			serviceJsoup.getInformationProduct(document, cssQLinkProductReview, cssQName, cssQImage, attribute, jedis,
					url, collectionProducts, gson, cssQRating, cssQPrice, cssQKeyWord, collectionCategories,
					categoryIds);
		} catch (ExceptionEmptyElement e) {
			e.printStackTrace();
			System.out.println("Format is not correct. Please check again and write again");
			addFormAndUseForm(document, sc, jedis, url, collectionProducts, mapCookies, gson, collectionCategories,
					categoryIds);
		}
		return productInfoFormDto;

	}

	public static String getHtml(String urlString, String userAgent) throws IOException {

//		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();		
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("51.83.47.73", 3128));
		URL url = new URL(urlString);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection(proxy);
		httpConn.addRequestProperty("User-Agent", userAgent);
		InputStream is = httpConn.getInputStream();
		int ptr = 0;
		StringBuffer html = new StringBuffer();
		while ((ptr = is.read()) != -1) {
			html.append((char) ptr);
		}
		return html.toString();

	}

}
