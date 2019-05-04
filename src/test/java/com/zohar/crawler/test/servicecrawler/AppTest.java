package com.zohar.crawler.test.servicecrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zohar.crawler.service.ServiceHelper;
import com.zohar.crawler.service.ServiceJsoup;

public class AppTest {

	public static ServiceHelper serviceHelper = new ServiceHelper();
	public static ServiceJsoup serviceJsoup = new ServiceJsoup();
	
	public static void main(String[] args) throws IOException {
		
		String url = "https://www.amazon.com/s?k=smartphone&lo=list&page=18&qid=1554450785&ref=sr_pg_18";
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";
		Document document = Jsoup.connect(url).userAgent(userAgent).cookies(serviceHelper.addCookies()).get();
		
		String cssQTitle = "div.col-xs-9 > h3.ugc-review-title.c-section-title.heading-5.v-fw-medium";
		String cssQHelpful = "div.feedback-display > button.pos-feedback.no-margin-l.false";
		String cssQDivParent = "div.reviews-content-wrapper > ul.reviews-list > li.review-item";
		
//		Elements elements = serviceJsoup.getElements(document, cssQDivParent);
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(
					new FileWriter("D:/IT/Generitas/CrawlerData/HtmlParsePageTest/test_jsoup.html", true));
			String content = document.html();
			writer.append(content);	
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Success!!!");
				
//		for(Element element : elements) {
//			String title = serviceJsoup.getContentFromElement(element, cssQTitle);
//			String helpful = serviceJsoup.getContentFromElement(element, cssQHelpful);
//			
//			System.out.println("Title is: " + title);
//			System.out.println("Helpful is: " + helpful);
//			System.out.println();
//		}
		
//		document.setBaseUri("https://images-na.ssl-images-amazon.com/");
//		
//		Element element = document.select("img#landingImage").first();
////		System.out.println("Normal 1");
//		String urlNew = element.attr("src");
////		System.out.println("Normal 2");
//		System.out.println("Url new is : " + urlNew);				
		
	}

}
