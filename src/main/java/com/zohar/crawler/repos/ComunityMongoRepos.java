package com.zohar.crawler.repos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;

public class ComunityMongoRepos {

	Logger LOGGER = Logger.getLogger(ComunityMongoRepos.class.getName());

	// update ducument in mongo
	public void updateOne(MongoCollection<Document> collection, Bson filter, Bson updateOperation) {

		collection.updateOne(filter, updateOperation);
		LOGGER.info("Insert product review link into Mongo sucess!");

	}

	// save document into mongo
	public void saveDocumentJson(MongoCollection<Document> collection, Document document) {

		collection.insertOne(document);
		LOGGER.info("Insert Json into Mongo success!");

	}

	// Get product_code
	public static List<String> getProductCodes(MongoCollection<Document> collection) {

		Projections projection = null;
		List<String> productCodes = new ArrayList<>();
		FindIterable findIterables = collection.find()
				.projection(projection.fields(projection.include("product_code"), projection.excludeId()));	
		Iterator iterator = findIterables.iterator();
		while (iterator.hasNext()) {			
			Document document = (Document) iterator.next();
			productCodes.add(document.getString("product_code"));
		}
		return productCodes;

	}
	
	// Get category and id
	public static HashMap<String, String> getIdAndCategory(MongoCollection<Document> collection) {
				
		HashMap<String, String> categoryIds = new HashMap<>();
		Projections projection = null;		
		FindIterable findIterables = collection.find()
				.projection(projection.fields(projection.include("name")));	
		Iterator iterator = findIterables.iterator();
		while (iterator.hasNext()) {
					
			Document document = (Document) iterator.next();
			String name = document.getString("name");
			String id = document.get("_id").toString();
			categoryIds.put(name, id);
			
		}
			
		return categoryIds;
		
	}

}
