package com.zohar.crawler.service;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.zohar.crawler.repos.ComunityMongoRepos;

public class ServiceMongo {

	Logger LOGGER = Logger.getLogger(ServiceMongo.class.getName());
	public ComunityMongoRepos comunityMongoRepos = new ComunityMongoRepos();

	// update ducument in mongo
	public void updateOne(MongoCollection<Document> collection, Bson filter, Bson updateOperation) {

		comunityMongoRepos.updateOne(collection, filter, updateOperation);

	}

	// save document into mongo
	public void saveDocumentJson(MongoCollection<Document> collection, Document document) {

		comunityMongoRepos.saveDocumentJson(collection, document);

	}

	// get product codes
	public List<String> getProductCodes(MongoCollection<Document> collection) {

		return comunityMongoRepos.getProductCodes(collection);

	}

	// get category and id
	public HashMap<String, String> getIdAndCategory(MongoCollection<Document> collection) {

		return comunityMongoRepos.getIdAndCategory(collection);

	}

}
