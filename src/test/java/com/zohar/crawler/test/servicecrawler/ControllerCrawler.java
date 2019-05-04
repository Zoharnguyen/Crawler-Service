package com.zohar.crawler.test.servicecrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class ControllerCrawler {

	public static void main(String[] args) throws Exception {
		String crawlStorageFolder = "/data/crawl/root";
		int numThread = 1;
		
		CrawlConfig config = new CrawlConfig();		
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(0);
		config.setMaxPagesToFetch(1);
		config.setPolitenessDelay(200);
		config.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
//		config.setProxyHost("82.137.244.7");
//		config.setProxyPort(23500);
		
		// Khai bao controller
		
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);		
		
//		int i = 1;
//		String productName = "smart+phone";
		
//		for(i = 1; i <= 20; i++) {   
//			controller.addSeed("https://www.amazon.com/s?k=" + productName + "&page=" +  i);  	
//		}				
		
		controller.addSeed("https://www.amazon.com/Net10-Samsung-Galaxy-Orbit-Prepaid/dp/B07GBX2M3Z/ref=cm_cr_arp_d_product_top?ie=UTF8");
		
//		CrawlController.WebCrawlerFactory<MyCrawler> factory = MyCrawler::new;   		
		
//		controller.start(factory, numThread);			
		
	}
}
