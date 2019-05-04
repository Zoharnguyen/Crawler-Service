package com.zohar.crawler.test.parseHtml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class GetHtml {

//	userAgents.add(
//			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246");
//	userAgents.add(
//			"Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36");
//	userAgents.add(
//			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/601.3.9 (KHTML, like Gecko) Version/9.0.2 Safari/601.3.9");
//	userAgents.add(
//			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36");
//	userAgents.add("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:15.0) Gecko/20100101 Firefox/15.0.1");

	public static void main(String[] args) throws IOException {

		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.0; tr-TR) AppleWebKit/533.18.1 (KHTML, like Gecko) Version/5.0.2 Safari/533.18.5";
		String urlString = "https://www.amazon.com/Battery-Compatible-ThinkPad-45N1750-45N1751/dp/B017X451F4";
		URL url = new URL(urlString);

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("51.83.47.73", 3128));
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection(proxy);
		
//		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.addRequestProperty("User-Agent", userAgent);
		
		InputStream is = httpConn.getInputStream();				
		int ptr = 0;
		StringBuffer html = new StringBuffer();
		while ((ptr = is.read()) != -1) {
			html.append((char) ptr);
		}
		
//		 get original folder of project
//		System.getProperty("user.dir") + "\\test.txt", true)

		BufferedWriter writerTest;
		try {
			writerTest = new BufferedWriter(
					new FileWriter("D:/IT/Generitas/CrawlerData/HtmlParsePageTest/test_originalFolder.html", true));
			writerTest.append(html.toString());			
			writerTest.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Done!");

	}

}
