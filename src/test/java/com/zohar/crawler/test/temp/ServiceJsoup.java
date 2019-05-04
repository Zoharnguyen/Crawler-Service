//package com.zohar.crawler.test.temp;
//
//import java.io.IOException;
//import java.net.SocketTimeoutException;
//import java.util.HashMap;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import redis.clients.jedis.Jedis;
//
//public class ServiceJsoup {
//
//	public ServiceHelper serviceHelper = new ServiceHelper();
//	public HashMap<String, String> mapCookies = new HashMap<>();
//	public String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";
//	public int countReview = 0;
//	public int countProduct = 0;
//	public int countPage = 0;
//	public int countProductLink = 0;
//
//	// get content from document use cssQuery
//	public String getContent(Document document, String cssQuery) throws IOException {
//
//		String content = document.select(cssQuery).first().text();
//
//		return content;
//
//	}
//
//	// get element from ducument use cssQuery
//	public Elements getElements(Document document, String cssQuery) {
//
//		Elements elements = document.select(cssQuery);
//
//		return elements;
//
//	}
//
//	// get content from element use cssQuery
//	public String getContentFromElement(Element element, String cssQuery) {
//
//		String content = element.select(cssQuery).text().replace("'", "");
//
//		return content.replace("\"", "");
//
//	}
//
//	public String getIdFromElement(Element element, String cssQuery) {
//
//		String id = element.select(cssQuery).attr("id");
//		return id;
//
//	}
//
//	public String getLinkImageFromElement(Element element, String cssQuery, String attribute) {
//
//		String linkImage = element.select(cssQuery).attr(attribute);
//		return linkImage;
//
//	}
//
//	public String getLinkHrefFromDocument(Document document, String cssQuery) {
//
//		String link = document.select(cssQuery).attr("href");
//		return link;
//
//	}
//
//	public String getLinkImageFromDocument(Document document, String cssQuery, String attribute) {
//
//		String linkImage = document.select(cssQuery).attr(attribute);
//		return linkImage;
//
//	}
//
//	// get productReview
//	public void getProductReview(Document document, String cssDivParent, String cssQTitle, String cssQReviewerName,
//			String cssQReviewerAvatar, String attribute, String cssQDate, String cssQContent, String cssQRating,
//			String cssQHelpful, String cssQId, String cssQNextPage, String url) {
//
//		Elements elements = getElements(document, cssDivParent);
////		System.out.println("Size of elements: " + elements.size());
//
//		for (Element element : elements) {
//
//			countReview++;
//			String title = "";
//			if(!cssQTitle.equals("")) title = getContentFromElement(element, cssQTitle);
//			String reviewerName = "";
//			if(!cssQReviewerName.equals("")) reviewerName = getContentFromElement(element, cssQReviewerName);
//			String reviewerAvatar = "";
//			if (!cssQReviewerAvatar.equals("")) reviewerAvatar = getLinkImageFromElement(element, cssQReviewerAvatar, attribute);
//			String date = "";
//			if(!cssQDate.equals("")) date = getContentFromElement(element, cssQDate);
//			String content = ""; 
//			if(!cssQContent.equals("")) content = getContentFromElement(element, cssQContent);
//			String rating = "";
//			if(!cssQRating.equals("")) rating = getContentFromElement(element, cssQRating);
//			String helpful = "";
//			if(!cssQHelpful.equals("")) helpful = getContentFromElement(element, cssQHelpful);
//			String id = "";
//			if (cssQId.equals("")) id = element.attr("id"); 
//			else id = getIdFromElement(element, cssQId);
//				
//			System.out.println("Review num: " + countReview);
//			System.out.println("Title is: " + title);
//			System.out.println("ReviewerName is: " + reviewerName);
//			System.out.println("Link avatar reveiwer: " + reviewerAvatar);
//			System.out.println("Date is: " + date);
//			System.out.println("Content is: " + content);
//			System.out.println("Rating is: " + rating);
//			System.out.println("Helpful is: " + helpful);
//			System.out.println("Id is: " + id);
//			System.out.println();
//		}
//
//		Element elementNextPage = document.select(cssQNextPage).first();
//		if (elementNextPage != null) {
//			String urlNew = serviceHelper.getDomain(url) + elementNextPage.attr("href");
//			Document document1;
//			try {
//				document1 = Jsoup.connect(urlNew).userAgent(userAgent).cookies(mapCookies).get();
//				getProductReview(document1, cssDivParent, cssQTitle, cssQReviewerName, cssQReviewerAvatar, attribute,
//						cssQDate, cssQContent, cssQRating, cssQHelpful, cssQId, cssQNextPage, urlNew);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println("Total review is: " + countReview);
//			System.out.println("End reviews this product.");
//		}
//	}
//
////	Get information about product
//	public void getInformationProduct(Document document, String cssQName, String cssQImage, String attribute,
//			String cssQRealPrice, String cssQSalePercent, String cssQRating, String cssQCountReview,
//			String cssQLinkProductReview, String cssQStatus, Jedis jedis, String url) throws IOException {
//		countProductLink++;
//		String realPrice = "";
//		String salePercent = "";
//		String name = "";
//		String image = "";
//		String rating = "";
//		String countReview = "";
//		String linkProductReview = "";
//		String status = "";
//		if (!cssQRealPrice.equals("")) realPrice = getContent(document, cssQRealPrice);
//		if (!cssQSalePercent.equals("")) salePercent = getContent(document, cssQSalePercent);
//		if (!cssQName.equals("")) name = getContent(document, cssQName);
//		if (!cssQImage.equals("")) image = getLinkImageFromDocument(document, cssQImage, attribute);
//		if (!cssQRating.equals("")) rating = getContent(document, cssQRating);
//		if (!cssQCountReview.equals("")) countReview = getContent(document, cssQCountReview);
//		if (!cssQLinkProductReview.equals("")) linkProductReview = serviceHelper.getDomain(url) + getLinkHrefFromDocument(document, cssQLinkProductReview);
//		if (!cssQStatus.equals("")) status = getContent(document, cssQStatus);
//
////		System.out.println("Name: " + name);
////		System.out.println("Link image: " + image);
////		System.out.println("RealPrice: " + realPrice);
////		System.out.println("SalePercent: " + salePercent);
////		System.out.println("Rating: " + rating);
////		System.out.println("CountReview: " + countReview);		
////		System.out.println("Status of product: " + status);
//		
//		System.out.println("Number link is: " + countProductLink);
//		System.out.println("Link product is: " + url);
//		System.out.println("Link product review: " + linkProductReview);		
//		jedis.sadd("productReviewLinks", linkProductReview);
//
//	}
//
////	Get link product detail
//	public void getLinkProducts(Document document, String cssQDivParent, String cssQLinkProduct, String cssQNextPage,
//			String url, Jedis jedis) {
//
//		countPage++;
//		Elements elements = document.select(cssQDivParent);
//		System.out.println("Totals are: " + elements.size());
//		for (Element element : elements) {
//			String linkProduct = serviceHelper.getDomain(url) + element.select(cssQLinkProduct).attr("href");
//			countProduct++;
//			System.out.println("Number: " + countProduct);
//			System.out.println("LinkProduct: " + linkProduct);
//			jedis.sadd("productInfoLinks", linkProduct);
//		}
//		Element elementNextPage = document.select(cssQNextPage).first();
//		if (elementNextPage != null) {
//			try {
//				String urlNew = serviceHelper.getDomain(url) + elementNextPage.attr("href");
//				Document document1 = Jsoup.connect(url).userAgent(userAgent).cookies(mapCookies).timeout(20000).get();
//				getLinkProducts(document1, cssQDivParent, cssQLinkProduct, cssQNextPage, urlNew, jedis);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println("Product end");
//		}
//		System.out.println("Number page: " + countPage);
//
//	}
//
//}
