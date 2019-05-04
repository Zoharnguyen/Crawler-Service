//package com.zohar.crawler.test.temp;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import com.zohar.crawler.service.ServiceHelper;
//import com.zohar.crawler.service.ServiceJsoup;
//
//public class ControllerJsoup {
//
//	public static ServiceHelper serviceHelper = new ServiceHelper();
//	public static ServiceJsoup serviceJsoup = new ServiceJsoup();
//
//	public static void main(String[] args) throws IOException {
//
////		intilize variable
//		HashMap<String, String> mapCookies = serviceHelper.addCookies();
//		String userAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML like Gecko) Chrome/44.0.2403.155 Safari/537.36";
//		String fileName = "D:/IT/Generitas/CrawlerData/FormCrawlerService/Product_Review_Amazon.txt";				
//		List<String> lists = serviceHelper.readFile(fileName);
//		
////		Get product review
//		
//		String url = lists.get(0);
//		String cssDivParent = lists.get(1);
//		String cssQTitle = lists.get(2);
//		String cssQReviewerName = lists.get(3);
//		String cssQReviewerAvatar = lists.get(4);
//		String atttribute = lists.get(5);
//		String cssQDate = lists.get(6);
//		String cssQContent = lists.get(7);
//		String cssQRating = lists.get(8);
//		String cssQHelpful = lists.get(9);
//		String cssQId = lists.get(10);
//		String cssQNextPage = lists.get(11);
//		Document document = Jsoup.connect(url).userAgent(userAgent).cookies(mapCookies).get();
//
//		serviceJsoup.getProductReview(document, cssDivParent, cssQTitle, cssQReviewerName, cssQReviewerAvatar, atttribute, cssQDate, cssQContent,
//				cssQRating, cssQHelpful, cssQId, cssQNextPage, url);
//		
////		Get product information		
//		
////		String url = lists.get(0);
////		String cssQName = lists.get(1);
////		String cssQImage = lists.get(2);
////		String attribute = lists.get(3);
////		String cssQRealPrice = lists.get(4);		
////		String cssQSalePercent = lists.get(5);
////		String cssQRating = lists.get(6);
////		String cssQCountReview = lists.get(7);
////		String cssQLinkProductReview = lists.get(8);
////		String cssQStatus = lists.get(9);
////		Document document = Jsoup.connect(url).userAgent(userAgent).cookies(mapCookies).timeout(20000).get();
////		
////		serviceJsoup.getInformationProduct(document, cssQName, cssQImage, attribute, cssQRealPrice, cssQSalePercent, cssQRating, cssQCountReview, cssQLinkProductReview, cssQStatus);
//
////		Get Link Product
//		
////		String url = lists.get(0);
////		String cssQDivParent = lists.get(1);
////		String cssQLinkProduct = lists.get(2);
////		String cscQNextPage = lists.get(3);
////		Document document = Jsoup.connect(url).userAgent(userAgent).cookies(mapCookies).timeout(20000).get();
////		
////		serviceJsoup.getLinkProducts(document, cssQDivParent, cssQLinkProduct, cscQNextPage, url);		
//		
//	}
//
//}
