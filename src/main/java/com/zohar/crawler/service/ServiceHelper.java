package com.zohar.crawler.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;

import com.google.gson.Gson;
import com.zohar.crawler.dto.ProductInfoFormDto;
import com.zohar.crawler.dto.ProductLinkFormDto;
import com.zohar.crawler.dto.ProductReviewFormDto;
import com.zohar.crawler.dto.ProductReviewRedisDto;

public class ServiceHelper {

	Logger LOGGER = Logger.getLogger(ServiceHelper.class.getName());

	// Create HashMap cookie

	public static HashMap<String, String> addCookies() {
		HashMap<String, String> mapCookies = new HashMap<>();
		mapCookies.put("AMCV_F6301253512D2BDB0A490D45%40AdobeOrg",
				"-330454231%7CMCMID%7C36694453828594357303992991607132473681%7CMCAID%7CNONE%7CMCOPTOUT-1553663671s%7CNONE%7CvVersion%7C3.1.2");
		mapCookies.put("BL_T_PROV", "undefined");
		mapCookies.put("UID", "ffbeb7a3-f2b2-4aa0-af2a-3fd72d2a94dd");
		mapCookies.put("optimizelyEndUserId", "oeu1553656475637r0.15074751989309354");
		mapCookies.put("BL_D_PROV", "undefined");
		mapCookies.put("oid", "572716967");
		mapCookies.put("AMCVS_F6301253512D2BDB0A490D45%40AdobeOrg", "1");
		mapCookies.put("CTT", "200ecbb8f04975566372fff061c9afb3");
		mapCookies.put("SID", "e4777018-39b5-4b21-9d87-f76090cb779f");
		mapCookies.put("c2", "no%20value");
		mapCookies.put("optimizelyProtocol", "https");
		mapCookies.put("bby_cbc_lb", "p-browse-e");
		mapCookies.put("bby_rdp", "l");
		mapCookies.put("intl_splash", "false");
		mapCookies.put("ltc", "%20");
		mapCookies.put("bby_prc_lb", "p-prc-w");
		return mapCookies;
	}

	// get domain from url
	public String getDomain(String url) {

		int endIndex = url.indexOf("com/") + 3;
		String domain = url.substring(0, endIndex);
		return domain;
	}

	// convert object to Json
	public String convertObjectToJson(Object object, Gson gson) {
		return standardJson(gson.toJson(object));
	}

	// convert json to ProductLinkForm
	public ProductLinkFormDto convertJsonToProductLinkForm(String json, Gson gson) {
		return gson.fromJson(json, ProductLinkFormDto.class);
	}

	// convert json to ProductInfoForm
	public ProductInfoFormDto convertJsonToProductInfoForm(String json, Gson gson) {
		return gson.fromJson(json, ProductInfoFormDto.class);
	}

	// convert json to ProductReviewFormDto
	public ProductReviewFormDto convertJsonToProductReviewForm(String json, Gson gson) {
		return gson.fromJson(json, ProductReviewFormDto.class);
	}

	// convert json to ProductReviewĐto
	public ProductReviewRedisDto convertJsonToProductReviewDto(String json, Gson gson) {
		return gson.fromJson(json, ProductReviewRedisDto.class);
	}

	// standardized json
	public String standardJson(String json) {
		return json.replace("\\u003e", ">");
	}

	// convert json to document
	public Document convertJsonToDocument(String json) {

		Document document = Document.parse(json);
		return document;

	}

//	https://www.amazon.com/Joselinen-Compatible-Magnetic-Wireless-Charging/dp/B07Q4HSG3W/ref=lp_16225009011_1_1?s=electronics&ie=UTF8&qid=1555516532&sr=1-1
	// get product code from url
	public static String getCode(String url) {

		int indexStart = url.indexOf("/dp/");
		return url.substring(indexStart + 4, url.length());

	}

	public static int convertRating(String rating) {

		rating = rating.replaceFirst("5", "");
		List<Character> list = new ArrayList<Character>();
		list.add('1');
		list.add('2');
		list.add('3');
		list.add('4');
		list.add('5');
		int ratingNum = 0;
		outloop: for (int i = 0; i < rating.length(); i++) {
			if (list.contains(rating.charAt(i))) {
				ratingNum = Character.getNumericValue(rating.charAt(i));
				break outloop;
			}
		}
		return ratingNum;

	}

	public static long convertCreatedAt(String createdAt) {

		HashMap<String, String> months = new HashMap<>();
		months.put("January", "1");
		months.put("February", "2");
		months.put("March", "3");
		months.put("April", "4");
		months.put("May", "5");
		months.put("June", "6");
		months.put("July", "7");
		months.put("August", "8");

		months.put("September", "9");
		months.put("October", "10");
		months.put("November", "11");
		months.put("December", "12");

		createdAt = createdAt.replace(",", "");
		createdAt = createdAt.replace(" ", "/");
		int indexEndMonth = createdAt.indexOf("/");
		String month = createdAt.substring(0, indexEndMonth);
		createdAt = createdAt.replace(month, months.get(month));

		Date date = null;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(createdAt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date.getTime();

	}

	public static String executeToGetImageLink(String images) {

		int indexStart = images.indexOf("http");
		int indexEnd = images.indexOf("jpg");
		String image = images.substring(indexStart, indexEnd + 3);
		return image;

	}

	// read file userAgent
	public List<String> userAgent(String fileName) {

		List<String> userAgents = new ArrayList<>();
		try (FileReader reader = new FileReader(fileName); BufferedReader br = new BufferedReader(reader)) {

			String line;
			while ((line = br.readLine()) != null) {
				userAgents.add(line);
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		return userAgents;

	}

	// write data in file exist
	public static void writeFileExisted(String fileName, String text) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.write(System.lineSeparator() + text);
		System.out.println("Add text into file sucess!");
		writer.close();

	}

	// get interger helpful
	public static int getLiked(String helpful) {

		int liked = 0;
		if (!helpful.equals("")) {
			try {
				int indexStart = helpful.indexOf(" people");
				String subHelpful = helpful.substring(0, indexStart);
				liked = Integer.parseInt(subHelpful);
			} catch (StringIndexOutOfBoundsException e) {
				liked = 1;
			}
		} else {
			liked = 0;
		}
		return liked;

	}

	public static double getProductRating(String rating) {

		double ratingDou = 0.0;
		int indexEnd = rating.indexOf(" out");
		String ratingSub = rating.substring(0, indexEnd);
		ratingDou = Double.parseDouble(ratingSub);

		return ratingDou;

	}

	// Customize url product infomation
	public static String customizeUrl(String url) {
		try {
			int indexEnd = url.indexOf("/ref");
			String urlNew = url.substring(0, indexEnd);
			return urlNew;
		} catch (java.lang.StringIndexOutOfBoundsException e) {
			System.out.println("Url: " + url + " Error: " + e.getMessage());
			return url;
		}
	}

	// Convert List proxy String to HashMap proxy with pair (Host, Port) = (String;
	// Integer)
	public static HashMap<String, Integer> convertListProxyToSetProxy(List<String> proxyList) {

		HashMap<String, Integer> proxyMap = new HashMap<String, Integer>();
		for (String proxy : proxyList) {

			int indexEnd = proxy.indexOf(":");
			String host = proxy.substring(0, indexEnd);
			int port = Integer.parseInt(proxy.substring(indexEnd + 1, proxy.length()));
			proxyMap.put(host, port);

		}
		return proxyMap;

	}

//	Read file
	public static List<String> readFile(String fileName) {

		List<String> lists = new ArrayList<String>();
		try (FileReader reader = new FileReader(fileName); BufferedReader br = new BufferedReader(reader)) {

			// read line by line
			String line;
			while ((line = br.readLine()) != null) {
				lists.add(line);
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		return lists;

	}

}
