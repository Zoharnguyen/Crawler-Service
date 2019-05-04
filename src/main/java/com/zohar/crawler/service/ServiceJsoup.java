package com.zohar.crawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.zohar.crawler.dto.CategoryDto;
import com.zohar.crawler.dto.ProductInfoDto;
import com.zohar.crawler.dto.ProductReviewDto;
import com.zohar.crawler.dto.ProductReviewRedisDto;
import com.zohar.crawler.help.ExceptionEmptyElement;

import redis.clients.jedis.Jedis;

public class ServiceJsoup {

	public Logger LOGGER = Logger.getLogger(ServiceJsoup.class.getName());

	public ServiceHelper serviceHelper = new ServiceHelper();
	public ServiceMongo serviceMongo = new ServiceMongo();
	public HashMap<String, String> mapCookies = new HashMap<>();
	public String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";
	public int countReview = 0;
	public int countProduct = 0;
	public int countPage = 0;
	public int countProductLink = 0;
	public ServiceRedis serviceRedis = new ServiceRedis();

	// get content from document use cssQuery
	public String getContent(Document document, String cssQuery) throws IOException {

		String content = document.select(cssQuery).first().text();

		return content;

	}

	// get element from ducument use cssQuery
	public Elements getElements(Document document, String cssQuery) {

		Elements elements = document.select(cssQuery);

		return elements;

	}

	// get content from element use cssQuery
	public String getContentFromElement(Element element, String cssQuery) {

		String content = element.select(cssQuery).text().replace("'", "");

		return content.replace("\"", "");

	}

	public String getIdFromElement(Element element, String cssQuery) {

		String id = element.select(cssQuery).attr("id");
		return id;

	}

	public String getLinkImageFromElement(Element element, String cssQuery, String attribute) {

		String linkImage = element.select(cssQuery).attr(attribute);
		return linkImage;

	}

	public String getLinkHrefFromDocument(Document document, String cssQuery) {

		String link = document.select(cssQuery).attr("href");
		return link;

	}

	public String getLinkImageFromDocument(Document document, String cssQuery, String attribute) {

		String linkImage = document.select(cssQuery).attr(attribute);
		return linkImage;

	}

	// get productReview
	public void getProductReview(Document document, String cssDivParent, String cssQTitle, String cssQReviewerName,
			String cssQReviewerAvatar, String attribute, String cssQDate, String cssQContent, String cssQRating,
			String cssQHelpful, String cssQId, String cssQNextPage, String url,
			MongoCollection<org.bson.Document> collection, Gson gson, ObjectId idObject) {

		Elements elements = getElements(document, cssDivParent);
		System.out.println("Size of elements: " + elements.size());

		for (Element element : elements) {

			countReview++;
			String title = "";
			if (!cssQTitle.equals(""))
				title = getContentFromElement(element, cssQTitle);
			String reviewerName = "";
			if (!cssQReviewerName.equals(""))
				reviewerName = getContentFromElement(element, cssQReviewerName);
			String reviewerAvatar = "";
			if (!cssQReviewerAvatar.equals(""))
				reviewerAvatar = getLinkImageFromElement(element, cssQReviewerAvatar, attribute).replaceAll("[\\n\\t ]",
						"");
			;
			String date = "";
			if (!cssQDate.equals(""))
				date = getContentFromElement(element, cssQDate);
			String content = "";
			if (!cssQContent.equals(""))
				content = getContentFromElement(element, cssQContent);
			String rating = "";
			if (!cssQRating.equals(""))
				rating = getContentFromElement(element, cssQRating);
			String helpful = "";
			if (!cssQHelpful.equals(""))
				helpful = getContentFromElement(element, cssQHelpful);
			String id = "";
			if (cssQId.equals(""))
				id = element.attr("id");
			else
				id = getIdFromElement(element, cssQId);

			System.out.println("Review num: " + countReview);
			System.out.println("Title is: " + title);
			System.out.println("ReviewerName is: " + reviewerName);
			System.out.println("Link avatar reveiwer: " + reviewerAvatar);
			System.out.println("Date is: " + date);
			System.out.println("Content is: " + content);
			System.out.println("Rating is: " + rating);
			System.out.println("Helpful is: " + helpful);
			System.out.println("Id is: " + id);
			System.out.println();
			ProductReviewDto productReviewDto = new ProductReviewDto(title, reviewerName, reviewerAvatar,
					serviceHelper.convertCreatedAt(date), content, serviceHelper.convertRating(rating),
					serviceHelper.getLiked(helpful), id, idObject.toString(), "", url);
			String json = serviceHelper.convertObjectToJson(productReviewDto, gson);
			System.out.println("Json: " + json);
			org.bson.Document documentTemp = serviceHelper.convertJsonToDocument(json);
			serviceMongo.saveDocumentJson(collection, documentTemp);

		}

		Element elementNextPage = document.select(cssQNextPage).first();
		if (elementNextPage != null) {
			String urlNew = serviceHelper.getDomain(url) + elementNextPage.attr("href");
			Document document1;
			try {
				document1 = Jsoup.connect(urlNew).userAgent(userAgent).cookies(mapCookies).get();
				getProductReview(document1, cssDivParent, cssQTitle, cssQReviewerName, cssQReviewerAvatar, attribute,
						cssQDate, cssQContent, cssQRating, cssQHelpful, cssQId, cssQNextPage, urlNew, collection, gson,
						idObject);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Total review is: " + countReview);
			System.out.println("End reviews this product.");
		}
	}

	public void getInformationProduct(Document document, String cssQLinkProductReview, String cssQName,
			String cssQImage, String attribute, Jedis jedis, String url,
			MongoCollection<org.bson.Document> collectionProducts, Gson gson, String cssQRating, String cssQPrice,
			String cssQKeyWord, MongoCollection<org.bson.Document> collectionCategories,
			HashMap<String, String> categoryIds) throws ExceptionEmptyElement, IOException {
		String productCode = serviceHelper.getCode(url);
		String productName = "";

		if (!cssQName.equals("")) {
			try {
				if (getContent(document, cssQName).equals("")) {
					throw new ExceptionEmptyElement("Incorrect css: " + cssQName);
				} else
					productName = getContent(document, cssQName);
			} catch (NullPointerException e) {
				throw new ExceptionEmptyElement("Null pointer exception at productName");
			}
		}

		String productPrice = "";
		try {
			productPrice = getContent(document, cssQPrice);
			if (productPrice.equals(""))
				throw new ExceptionEmptyElement("Not price");
		} catch (NullPointerException | ExceptionEmptyElement ePrice) {

			throw new ExceptionEmptyElement("Not price");

		}

		List<String> productCodes = serviceMongo.getProductCodes(collectionProducts);
		if (!productCode.equals("") && !productCodes.contains(productCode)) {
			System.out.println("Link product is: " + url);
			ProductInfoDto productInfoDto = new ProductInfoDto();

			String productRating = "";
			try {
				if (getContent(document, cssQRating).equals("")) {
					throw new ExceptionEmptyElement("Incorrect css: " + cssQRating);
				} else
					productRating = getContent(document, cssQRating);
			} catch (NullPointerException e) {
				throw new ExceptionEmptyElement("Null pointer exception at productRating");
			}

			String linkProductReview = "";
			if (!cssQLinkProductReview.equals("")) {
				try {
					if (getLinkHrefFromDocument(document, cssQLinkProductReview).equals("")) {
						throw new ExceptionEmptyElement("Incorrect css: " + cssQLinkProductReview);
					} else
						linkProductReview = serviceHelper.getDomain(url)
								+ getLinkHrefFromDocument(document, cssQLinkProductReview);
				} catch (NullPointerException e) {
					throw new ExceptionEmptyElement("Null pointer exception at productLink");
				}
			}
			String productImage = "";
			String temp = "No";
			String categoryId = "";
			if (!cssQImage.equals("")) {
				try {
					if (getLinkImageFromDocument(document, cssQImage, attribute).equals("")) {
						throw new ExceptionEmptyElement("Incorrect css: " + cssQImage);
					} else {
						productImage = serviceHelper
								.executeToGetImageLink(getLinkImageFromDocument(document, cssQImage, attribute));
						temp = "Yes";
					}
				} catch (NullPointerException e) {
					throw new ExceptionEmptyElement("Null pointer exception at productImage");
				}
			}

			Elements elementsKeyWords = getElements(document, cssQKeyWord);
			List<String> categories = new ArrayList<>();
			categoryIds = serviceMongo.getIdAndCategory(collectionCategories);
			for (int i = 0; i < elementsKeyWords.size(); i++) {

				if (i % 2 == 0) {
					categories.add(elementsKeyWords.get(i).select("span > a").text());
				}

			}
			// save category into table categories
			for (int i = 0; i < categories.size(); i++) {
				CategoryDto categoryDto = null;
				if (categoryIds != null && !categoryIds.containsKey(categories.get(i))) {
					if (i == 0) {
						categoryDto = new CategoryDto(categories.get(i), "0");
					} else {
						categoryDto = new CategoryDto();
						String idParent = categoryIds.get(categories.get(i - 1));
						categoryDto.setName(categories.get(i));
						categoryDto.setParent(idParent);
					}
				} else if (categoryIds == null) {
					categoryDto = new CategoryDto(categories.get(i), "0");
				} else if (i == categories.size() - 1 && categoryIds.containsKey(categories.get(i))) {
					String name = categories.get(i);
					categoryDto = new CategoryDto(name, categoryIds.get(name));
				}
				if (categoryDto != null) {
					String json = serviceHelper.convertObjectToJson(categoryDto, gson);
					org.bson.Document documentTemp = serviceHelper.convertJsonToDocument(json);
					serviceMongo.saveDocumentJson(collectionCategories, documentTemp);
					ObjectId id = documentTemp.getObjectId("_id");
					categoryIds.put(categories.get(i), id.toString());
					if (i == categories.size() - 1) {
						categoryId = id.toString();
					}
				}

			}

			countProductLink++;
			System.out.println("Number link is: " + countProductLink);
			System.out.println("Link product review: " + linkProductReview);
			System.out.println("Product name is : " + productName);
			System.out.println("Product image is : " + temp);
			System.out.println("Product price is: " + productPrice);
			System.out.println("Product rating is: " + productRating);
			// save product review link into mongo
			productInfoDto.setProduct_info_link(url);
			productInfoDto.setProduct_review_link(linkProductReview);
			productInfoDto.setProduct_name(productName);
			productInfoDto.setProduct_image(productImage);
			productInfoDto.setProduct_code(productCode);
			productInfoDto.setProduct_price(productPrice);
			productInfoDto.setProduct_rating(serviceHelper.getProductRating(productRating));
			productInfoDto.setProduct_category(categoryId);

			String json = serviceHelper.convertObjectToJson(productInfoDto, gson);
			org.bson.Document documentTemp = serviceHelper.convertJsonToDocument(json);
			serviceMongo.saveDocumentJson(collectionProducts, documentTemp);
			// save product review dto in redis
			ObjectId id = documentTemp.getObjectId("_id");
			ProductReviewRedisDto productReviewRedisDto = new ProductReviewRedisDto(linkProductReview, id);
			serviceRedis.addObjectForm(jedis, gson, productReviewRedisDto, "productReviewDto");

		}
	}

//	Get link product detail
	public void getLinkProducts(Document document, String cssQDivParent, String cssQLinkProduct, String cssQNextPage,
			String url, Jedis jedis) {

		countPage++;
		Elements elements = document.select(cssQDivParent);

		System.out.println("Totals are: " + elements.size());
		for (Element element : elements) {
			String linkProduct = serviceHelper.getDomain(url) + element.select(cssQLinkProduct).attr("href");
			countProduct++;
			System.out.println("Number: " + countProduct);
			System.out.println("LinkProduct: " + linkProduct);
			if (!element.select("span.a-icon-alt").text().equals("")) {
				jedis.sadd("productInfoLinks", linkProduct);
			}
		}
		Element elementNextPage = document.select(cssQNextPage).first();
		if (elementNextPage != null) {
			try {
				String urlNew = serviceHelper.getDomain(url) + elementNextPage.attr("href");
				Document document1 = Jsoup.connect(url).userAgent(userAgent).cookies(mapCookies).timeout(20000).get();
				getLinkProducts(document1, cssQDivParent, cssQLinkProduct, cssQNextPage, urlNew, jedis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Product end");
		}
		System.out.println("Number page: " + countPage);

	}

}
