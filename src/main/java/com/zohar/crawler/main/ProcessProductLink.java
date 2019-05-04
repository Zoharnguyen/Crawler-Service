package com.zohar.crawler.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.zohar.crawler.dto.ProductLinkFormDto;
import com.zohar.crawler.service.ServiceHelper;
import com.zohar.crawler.service.ServiceJsoup;
import com.zohar.crawler.service.ServiceRedis;

import redis.clients.jedis.Jedis;

public class ProcessProductLink {

	public static ServiceHelper serviceHelper = new ServiceHelper();
	public static ServiceJsoup serviceJsoup = new ServiceJsoup();
	public static ServiceRedis serviceRedis = new ServiceRedis();
	public static Logger LOGGER = Logger.getLogger(ProcessProductLink.class.getName());

	public static void main(String[] args) throws IOException {
				
//		Initialize variable

		HashMap<String, String> mapCookies = serviceHelper.addCookies();
		List<String> userAgents = new ArrayList<String>();
		userAgents.add(
				"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.3 Safari/533.19.4");
		userAgents.add(
				"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.3 Safari/533.19.4");
		Jedis jedis = new Jedis();
		Gson gson = new Gson();
		String key = "productLinkForms";
		String url = "https://www.amazon.com/s?k=computer&i=computers&ref=nb_sb_noss_2";
		
		List<ProductLinkFormDto> productLinkFormDtos = serviceRedis.getListProductLinkForms(jedis, gson, key);		
		Scanner sc = new Scanner(System.in);
		Document document = Jsoup.connect(url)
								.userAgent(userAgents.get(0))
								.cookies(mapCookies)
//								.proxy("82.207.119.200", 1080)
								.timeout(20000).get();	

//		Get Link Product		
		System.out.println("Size of productLinkFormDtos: " + productLinkFormDtos.size());
		for (int i = 0; i < productLinkFormDtos.size(); i++) {
			try {

				String cssQDivParent = productLinkFormDtos.get(i).getDivParent();
				String cssQLinkProduct = productLinkFormDtos.get(i).getLinkProduct();
				String cssQNextPage = productLinkFormDtos.get(i).getNextPage();			

				serviceJsoup.getLinkProducts(document, cssQDivParent, cssQLinkProduct, cssQNextPage, url, jedis);

			} catch (NullPointerException e) {
				System.out.println("Incorrect format. Please add new format!");
				System.out.println("Url: " + url);				
				if (i == productLinkFormDtos.size() - 1) {
					ProductLinkFormDto productLinkFormDto = addFormAndUse(sc, document, url, jedis);
					serviceRedis.addObjectForm(jedis, gson, productLinkFormDto, key);
					productLinkFormDtos = serviceRedis.getListProductLinkForms(jedis, gson, key);
				}
			}
		}
	}

//	add new form product link page
	public static ProductLinkFormDto addFormAndUse(Scanner sc, Document document, String url, Jedis jedis) {
		ProductLinkFormDto productLinkFormDto = null;
		try {
			System.out.println("DivParent: ");
			String cssQDivParent = sc.nextLine();
			System.out.println("LinkProduct: ");
			String cssQLinkProduct = sc.nextLine();
			System.out.println("NextPage: ");
			String cssQNextPage = sc.nextLine();			
			productLinkFormDto = new ProductLinkFormDto(cssQDivParent, cssQLinkProduct, cssQNextPage);
			serviceJsoup.getLinkProducts(document, cssQDivParent, cssQLinkProduct, cssQNextPage, url, jedis);
		} catch (NullPointerException e) {
			System.out.println("Incorrect form. Please fill again");
			addFormAndUse(sc, document, url, jedis);
		}
		return productLinkFormDto;
	}
}
