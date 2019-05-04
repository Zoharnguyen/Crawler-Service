package com.zohar.crawler.test.executionFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExecutionFileTest {

	public static List<String> readFile(String fileName) {
		
		List<String> lists = new ArrayList<String>();
		 try (FileReader reader = new FileReader(fileName);
	             BufferedReader br = new BufferedReader(reader)) {

	            // read line by line
	            String line;
	            while ((line = br.readLine()) != null) {
	                lists.add(line);
	            }

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
		return lists;
		
	}
	
	public static void main(String[] args) {
		
		String fileName = "D:/IT/Generitas/CrawlerData/Data/proxy.docx";
		List<String> proxies = readFile(fileName);
		System.out.println("Size: " + proxies.size());
		
	}
	
}
