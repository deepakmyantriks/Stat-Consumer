package com.yantriks.statistics.consumer.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yantriks.statistics.consumer.repository.OrderStatisticsOperations;

@RestController
@RequestMapping(value="orderstatistics")
public class OrderStatistcsController {

	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	 @Autowired
	 OrderStatisticsOperations orderStatisticsOps;
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/create")
	 public String create(@RequestBody String orderStatisticsMsg)
	 {
		logger.debug("inserting the messge in mongodb: \n"+ orderStatisticsMsg);

		orderStatisticsOps.insertOrderStatistics(orderStatisticsMsg);
		
		return "Record updated successfully";
	 }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/getStatistics", produces ={"application/json"})
	 public String getAllStatistics() throws JsonProcessingException
	 {
		logger.debug("getAllStatistics from mongodb");
		///JSONParser parser = new JSONParser();
		return orderStatisticsOps.getAllStatistics();
	 }

	 /*@RequestMapping(method = RequestMethod.GET, value = "/getStatistics/{data}", produces ={"application/json"})
	 public String getAllStatistics(@PathVariable String data) throws JsonProcessingException
	 {
		logger.debug("getAllStatistics from mongodb for data: "+ data);
		String[] strDataArray=data.split("=");
		String attributeName=strDataArray[0];
		String attributeValue=strDataArray[1];
		
		
		return orderStatisticsOps.getOrderStatisticsData(attributeName, attributeValue);
	 } */

}
