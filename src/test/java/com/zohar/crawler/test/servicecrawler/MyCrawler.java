package com.zohar.crawler.test.servicecrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

	public Logger LOGGER = Logger.getLogger(MyCrawler.class.getName());

	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp4|zip|gz))$");

	private final static Pattern filterGetLinkProductDetails = Pattern
			.compile("https:\\/\\/www\\.amazon\\.com\\/(.)+\\/dp\\/(.)+\\/ref=");

	private final static Pattern filterGetLinkProductReviews = Pattern
			.compile("https:\\/\\/www\\.amazon\\.com\\/(.)+\\/product\\-reviews\\/(.)+");

	List<String> productNames;

	Set<String> linkProductDetails = new HashSet<String>();
	int countSV = 0;
	int countV = 0;
	int timeRun = 0;

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {

		String pageUrl = referringPage.getWebURL().getURL().toLowerCase();
		String href = url.getURL().toLowerCase();

		boolean result = filterGetLinkProductDetails.matcher(href).find();

		return !FILTERS.matcher(href).matches() && href.startsWith("https://www.amazon.com/");
	}

	@Override
	public void visit(Page page) {
		LOGGER.log(Level.INFO, "Page visiting: " + page.getWebURL().getURL());
		countV++;
		System.out.println("Total number page Visited : " + countV);
		String url = page.getWebURL().getURL();

		if (page.getParseData() instanceof HtmlParseData) {

			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();

			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter("D:/IT/Generitas/CrawlerData/Link_Amazon.txt", true));
				String content = html;
				writer.append(content);
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
