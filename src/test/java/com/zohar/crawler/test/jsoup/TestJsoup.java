package com.zohar.crawler.test.jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.zohar.crawler.help.ExceptionEmptyElement;
import com.zohar.crawler.service.ServiceHelper;

public class TestJsoup {

	public static ServiceHelper serviceHelper = new ServiceHelper();

	public static void main(String[] args) throws IOException {

//		Get product-review link
		
		String userAgent = "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25";
		String urlString = "https://hidemyna.me/en/proxy-list/";
		URL url = new URL(urlString);
		
//		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//		httpConn.addRequestProperty("User-Agent",userAgent);
//		InputStream is = httpConn.getInputStream();
//		int ptr = 0;
//		StringBuffer html = new StringBuffer();
//		while ((ptr = is.read()) != -1) {
//			html.append((char)ptr);
//		}	
//		
//		System.out.println(html);
//      
//		Document document = Jsoup.parse(html.toString());
		
		Document document = Jsoup.connect(urlString).timeout(20000).userAgent(userAgent).cookie("fuck", "You").get();
		
		String cssQ = "table.proxy__t > tbody > td.tdl";
		String attr = "src";
		Element element = document.select(cssQ).first();
		System.out.println("Host " + element.text());

		
	}

}
