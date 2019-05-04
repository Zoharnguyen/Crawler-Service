package com.zohar.crawler.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zohar.crawler.dto.ProductInfoFormDto;
import com.zohar.crawler.help.ExceptionEmptyElement;
import com.zohar.crawler.service.ServiceHelper;
import com.zohar.crawler.service.ServiceJsoup;
import com.zohar.crawler.service.ServiceMongo;
import com.zohar.crawler.service.ServiceRedis;

import redis.clients.jedis.Jedis;

public class ProcessProductInfo {

	public static Logger LOGGER = Logger.getLogger(ProcessProductInfo.class.getName());

	public static ServiceHelper serviceHelper = new ServiceHelper();
	public static ServiceJsoup serviceJsoup = new ServiceJsoup();
	public static ServiceRedis serviceRedis = new ServiceRedis();
	public static ServiceMongo serviceMongo = new ServiceMongo();
	public static int proxyChangeStatus = 0;
	public static String host = "";
	public static int port = 0;

	public static void main(String[] args) throws IOException {

//		initialize variable
		HashMap<String, String> mapCookies = serviceHelper.addCookies();
		String fileName = System.getProperty("user.dir") + "\\UserAgents.txt";
		Jedis jedis = new Jedis();
		Gson gson = new Gson();
		String key = "productInfoForms";
		Scanner sc = new Scanner(System.in);
		String keyUrlProductInfos = "productInfoLinks";
		String fileLogErrors = System.getProperty("user.dir") + "\\LogErrors.txt";
		String fileProxy = System.getProperty("user.dir") + "\\Proxy.txt";
		List<String> proxyList = serviceHelper.readFile(fileProxy);
		HashMap<String, Integer> proxyMap = serviceHelper.convertListProxyToSetProxy(proxyList);
		// Set up proxy
		Set<String> keySet = proxyMap.keySet();
		List<String> keyList = new ArrayList<String>(keySet);
		host = keyList.get(0);
		port = proxyMap.get(host);

//		MongoClient mongoClient = new MongoClient("localhost", 27017);
//		MongoDatabase database = mongoClient.getDatabase("crawler_version_0-0-1");
		MongoClientURI uri = new MongoClientURI("mongodb://review:re%401view!@generitas.vn:27017/review");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("review");
		MongoCollection<org.bson.Document> collectionProducts = database.getCollection("test_products");
		MongoCollection<org.bson.Document> collectionCategories = database.getCollection("test_categories");
		HashMap<String, String> categoryIds = serviceMongo.getIdAndCategory(collectionCategories);
		int numUserAgent = 1;
		if (jedis.exists("userAgents") == false) {
			List<String> userAgents = serviceHelper.userAgent(fileName);
			jedis.rpush("userAgents", (String[]) userAgents.toArray(new String[0]));
			System.out.println("Success");
		}

//		Get product information		

		List<ProductInfoFormDto> productInfoFormDtos = serviceRedis.getListProductInfoForms(jedis, gson, key);
		String userAgent = jedis.lpop("userAgents");

		while (serviceRedis.getAllMember(jedis, keyUrlProductInfos).size() > 0) {

			String url = (String) serviceRedis.popElement(jedis, keyUrlProductInfos);
			url = serviceHelper.customizeUrl(url);

			Document document = Jsoup.parse(getHtml(url, userAgent, proxyMap));
			outFor: {
				for (int i = 0; i < productInfoFormDtos.size(); i++) {

					String cssQLinkProductReview = productInfoFormDtos.get(i).getLinkReview();
					String cssQName = productInfoFormDtos.get(i).getProductName();
					String cssQImage = productInfoFormDtos.get(i).getProductImage();
					String attribute = productInfoFormDtos.get(i).getProductImageAttri();
					String cssQPrice = productInfoFormDtos.get(i).getProductPrice();
					String cssQRating = productInfoFormDtos.get(i).getProductRating();
					String cssQKeyWord = productInfoFormDtos.get(i).getKeyWord();

					try {

						serviceJsoup.getInformationProduct(document, cssQLinkProductReview, cssQName, cssQImage,
								attribute, jedis, url, collectionProducts, gson, cssQRating, cssQPrice, cssQKeyWord,
								collectionCategories, categoryIds);
						break outFor;

					} catch (ExceptionEmptyElement e) {
						e.printStackTrace();
						System.out.println("Link url: " + url);
					}
					// If try all form not ok must to be add new form.
					if (i == productInfoFormDtos.size() - 1) {
						try {
							userAgent = jedis.lpop("userAgents");
							Document document1 = Jsoup.parse(getHtml(url, userAgent, proxyMap));
							serviceJsoup.getInformationProduct(document1, cssQLinkProductReview, cssQName, cssQImage,
									attribute, jedis, url, collectionProducts, gson, cssQRating, cssQPrice, cssQKeyWord,
									collectionCategories, categoryIds);
							break;
						} catch (ExceptionEmptyElement e) {
							String text = "Link: " + url + " Error: " + e.getMessage();
							if (e.getMessage().equals("Null pointer exception at productName")) {
								proxyChangeStatus = 1;
							} else {
								serviceHelper.writeFileExisted(fileLogErrors, text);
							}
						}
					}
				}
			}
		}

	}

	public static String getHtml(String urlString, String userAgent, HashMap<String, Integer> proxyMap) {

		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// When proxy die user other proxy
		HttpURLConnection httpConn = null;
		outLoop: while (true) {
			if (proxyChangeStatus == 1) {
				proxyMap.remove(host, port);
				Set<String> keySet = proxyMap.keySet();
				List<String> keyList = new ArrayList<String>(keySet);
				host = keyList.get(0);
				port = proxyMap.get(host);
				proxyChangeStatus = 0;
			}
			try {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
				httpConn = (HttpURLConnection) url.openConnection(proxy);
				break outLoop;

			} catch (IOException e) {
				// if proxy fail delete from map and use other proxy in map
				System.out.println("Proxy fail: " + e.getMessage() + " Proxy is: " + host + ":" + port);
				proxyMap.remove(host, port);
				Set<String> keySet = proxyMap.keySet();
				List<String> keyList = new ArrayList<String>(keySet);
				host = keyList.get(0);
				port = proxyMap.get(host);
			}

		}

		httpConn.addRequestProperty("User-Agent", userAgent);
		InputStream is;
		StringBuffer html = new StringBuffer();
		try {
			is = httpConn.getInputStream();
			int ptr = 0;
			while ((ptr = is.read()) != -1) {
				html.append((char) ptr);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Proxy fail: " + e.getMessage() + " Proxy is: " + host + ":" + port);
		}

		return html.toString();

	}

}
