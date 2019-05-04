//package com.zohar.crawler.test.temp;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Scanner;
//import java.util.Set;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import com.google.gson.Gson;
//import com.zohar.crawler.dto.ProductInfoFormDto;
//import com.zohar.crawler.service.ServiceHelper;
//import com.zohar.crawler.service.ServiceJsoup;
//import com.zohar.crawler.service.ServiceRedis;
//
//import redis.clients.jedis.Jedis;
//
//public class ControllerProductInfo {
//
//	public static ServiceHelper serviceHelper = new ServiceHelper();
//	public static ServiceJsoup serviceJsoup = new ServiceJsoup();
//	public static ServiceRedis serviceRedis = new ServiceRedis();
//
//	public static void main(String[] args) throws IOException {
//
////		initialize variable
//		HashMap<String, String> mapCookies = serviceHelper.addCookies();
//		String userAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/44.0.2403.155 Safari/537.36";
////		String url = "https://www.amazon.com/Death-Wish-Bruce-Willis/dp/B07B3JFQH4/ref=sr_1_289?keywords=movie&qid=1554798889&s=gateway&sr=8-289";
////		String urlFirst = "https://www.amazon.com/Dell-Inspiron-5000-Touchscreen-Business/dp/B07FRPLYCG/ref=sr_1_3?keywords=laptop&qid=1554293351&s=gateway&sr=8-3";
//		Jedis jedis = new Jedis();
//		Gson gson = new Gson();
//		String key = "productInfoForms";
//		Scanner sc = new Scanner(System.in);
//		String keyUrlProductInfos = "productInfoLinks";
//
////		Test add product info form into redis
//		ProductInfoFormDto productInfoFormDtoInitialize = new ProductInfoFormDto();
////		productInfoFormDtoInitialize.setName("span#productTitle");
////		productInfoFormDtoInitialize.setImage("div#imgTagWrapperId > img");
////		productInfoFormDtoInitialize.setImageAttri("data-old-hires");
////		productInfoFormDtoInitialize.setRealPrice("span#priceblock_ourprice");
////		productInfoFormDtoInitialize.setSalePercent(
////				"tr#regularprice_savings > td.a-span12.a-color-price.a-size-base.priceBlockSavingsString");
////		productInfoFormDtoInitialize.setRating("i.a-icon.a-icon-star.a-star-4 > span.a-icon-alt");
////		productInfoFormDtoInitialize.setCountReview("span#acrCustomerReviewText");
////		productInfoFormDtoInitialize.setStatus("div#availability > span.a-size-medium.a-color-success");
//		
////		productInfoFormDtoInitialize.setLinkReview("a#dp-summary-see-all-reviews");
//
//		serviceRedis.addObjectForm(jedis, gson, productInfoFormDtoInitialize, key);
//
////		Get product information		
//
//		List<ProductInfoFormDto> productInfoFormDtos = serviceRedis.getListProductInfoForms(jedis, gson, key);
//
//		while (serviceRedis.getAllMember(jedis, keyUrlProductInfos).size() > 0) {
//
//			String url = serviceRedis.popElement(jedis, keyUrlProductInfos);
//			Document document = Jsoup.connect(url).userAgent(userAgent).cookies(mapCookies).timeout(20000).get();
//			for (int i = 0; i < productInfoFormDtos.size(); i++) {
//
//				try {
//					String cssQName = productInfoFormDtos.get(i).getName();
//					String cssQImage = productInfoFormDtos.get(i).getImage();
//					String attribute = productInfoFormDtos.get(i).getImageAttri();
//					String cssQRealPrice = productInfoFormDtos.get(i).getRealPrice();
//					String cssQSalePercent = productInfoFormDtos.get(i).getSalePercent();
//					String cssQRating = productInfoFormDtos.get(i).getRating();
//					String cssQCountReview = productInfoFormDtos.get(i).getCountReview();
//					String cssQLinkProductReview = productInfoFormDtos.get(i).getLinkReview();
//					String cssQStatus = productInfoFormDtos.get(i).getStatus();
//
//					serviceJsoup.getInformationProduct(document, cssQName, cssQImage, attribute, cssQRealPrice,
//							cssQSalePercent, cssQRating, cssQCountReview, cssQLinkProductReview, cssQStatus, jedis, url);
//					break;
//				} catch (NullPointerException e) {
//					System.out.println("Null pointer!!! Don't have form for this url. Please add more: ");
//					System.out.println("Link url: " + url);
//					if (i == productInfoFormDtos.size() - 1) {
//						ProductInfoFormDto productInfoFormDto = addFormAndUseForm(document, sc, jedis, url);
//						serviceRedis.addObjectForm(jedis, gson, productInfoFormDto, key);
//						productInfoFormDtos = serviceRedis.getListProductInfoForms(jedis, gson, key);
//					}
//				}
//			}
//		}
//	}
//
////	add correct format product information
//	public static ProductInfoFormDto addFormAndUseForm(Document document, Scanner sc, Jedis jedis, String url) {
//		ProductInfoFormDto productInfoFormDto = null;
//		try {
//			System.out.println("CssQName: ");
//			String cssQName = sc.nextLine();
//			System.out.println("CssQImage: ");
//			String cssQImage = sc.nextLine();
//			System.out.println("Attribute: ");
//			String attribute = sc.nextLine();
//			System.out.println("CssQRealPrice: ");
//			String cssQRealPrice = sc.nextLine();
//			System.out.println("CssQSalePercent: ");
//			String cssQSalePercent = sc.nextLine();
//			System.out.println("CssQRating: ");
//			String cssQRating = sc.nextLine();
//			System.out.println("CssQCountReview: ");
//			String cssQCountReview = sc.nextLine();
//			System.out.println("CssQLinkProductReview: ");
//			String cssQLinkProductReview = sc.nextLine();
//			System.out.println("Status: ");
//			String cssQStatus = sc.nextLine();
//
//			productInfoFormDto = new ProductInfoFormDto(cssQName, cssQImage, attribute, cssQRealPrice, cssQSalePercent,
//					cssQRating, cssQCountReview, cssQLinkProductReview, cssQStatus);
//			serviceJsoup.getInformationProduct(document, cssQName, cssQImage, attribute, cssQRealPrice, cssQSalePercent,
//					cssQRating, cssQCountReview, cssQLinkProductReview, cssQStatus, jedis, url);
//		} catch (NullPointerException | IOException e) {
//			System.out.println("Format is not correct. Please check again and write again");
//			addFormAndUseForm(document, sc, jedis, url);
//		}
//		return productInfoFormDto;
//	}
//
//}
