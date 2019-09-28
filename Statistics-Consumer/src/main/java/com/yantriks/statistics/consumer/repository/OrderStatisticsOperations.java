package com.yantriks.statistics.consumer.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.yantriks.statistics.consumer.config.MongoDBConfig;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class OrderStatisticsOperations {

	 private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MongoDBConfig mongoConfig;

	@Autowired
	MongoTemplate mongoTemp;


	public void insertOrderStatistics(String orderStatMsg) {

		long startime = System.currentTimeMillis();

		mongoTemp.insert(orderStatMsg, mongoConfig.getCollection());
		
		long endTime = System.currentTimeMillis();
		logger.debug("Time taken to insert the record in mongoDB"+ (endTime-startime));



	}

	public String getAllStatistics() throws JsonProcessingException {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.fields().exclude("_id");

		long startime = System.currentTimeMillis();

		List<String> listOfOrderStatistics = mongoTemp.find(query,String.class,mongoConfig.getCollection());


		long endTime = System.currentTimeMillis();
		logger.debug("Time taken to get all records from mongoDB"+ (endTime-startime));

		logger.debug(" listOfOrderStatistics : "+listOfOrderStatistics );
		
		
		JsonParser jsonParser=new JsonParser();
		JsonArray listOfOrderStatisticsInJsonArray = jsonParser.parse(listOfOrderStatistics.toString()).getAsJsonArray();
		logger.debug(listOfOrderStatisticsInJsonArray.toString());


		return listOfOrderStatisticsInJsonArray.toString();
	}

	/*public String getOrderStatisticsData(String strAttributeName, String strAttributeValue) {
		Query query = new Query();
		
		logger.debug("AttributeName : "+strAttributeName );
		logger.debug("AttributeValue : "+strAttributeValue);

		query.with(new Sort(Sort.Direction.ASC, mongoConfig.getSortingKey()));
		
		query.addCriteria(Criteria.where(strAttributeName).is(strAttributeValue));
		query.fields().exclude("_id");
		long startime = System.currentTimeMillis();

		List<String> listOfOrderStatistics = mongoTemp.find(query,String.class,mongoConfig.getCollection());
		
		long endTime = System.currentTimeMillis();
		logger.debug("Time taken to get records from mongoDB"+ (endTime-startime));

		logger.debug(" listOfOrderStatistics : "+listOfOrderStatistics );

		JsonParser jsonParser=new JsonParser();
		JsonArray listOfOrderStatisticsInJsonArray = jsonParser.parse(listOfOrderStatistics.toString()).getAsJsonArray();
		logger.debug(listOfOrderStatisticsInJsonArray.toString());
		return listOfOrderStatisticsInJsonArray.toString();
	} */

}
