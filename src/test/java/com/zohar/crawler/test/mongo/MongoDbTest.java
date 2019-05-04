package com.zohar.crawler.test.mongo;

import java.net.UnknownHostException;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDbTest {

	public static void main(String[] args) throws UnknownHostException {

		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("crawler");
		MongoCollection<Document> collection = database.getCollection("ProductInfo");		
		
	}

}
