package com.yantriks.statistics.consumer.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yantriks.statistics.consumer.config.KafkaConfig;
import com.yantriks.statistics.consumer.constants.OrderStatisticsConstants;
import com.yantriks.statistics.consumer.processor.KafkaProcessor;
import com.yantriks.statistics.consumer.processor.RestProcessor;


@Component
public class KafkaToRestRouteBuilder extends RouteBuilder {

	@Autowired
	KafkaConfig kakfaConfig;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void configure() throws Exception {

		logger.debug("Kafka Port: " +kakfaConfig.getKafkaPort());
		logger.debug("Kafka Server: " +kakfaConfig.getKafkaUrl() );
		logger.debug("Kafka Topic: " +kakfaConfig.getKafkaTopic());
		logger.debug("Kafka read messages from begining: " +kakfaConfig.getAutoOffsetReset() 
		+" for GroupId "+ kakfaConfig.getGroupId());
	
		//Read message from kafka topic and insert the record in MongoDB via rest api
		from("kafka:"+kakfaConfig.getKafkaTopic() +
				"?brokers="
				+ kakfaConfig.getKafkaUrl() +":"+ kakfaConfig.getKafkaPort()
				+"&groupId="+kakfaConfig.getGroupId()+ 
				"&autoOffsetReset="	+ kakfaConfig.getAutoOffsetReset())
		.routeId(OrderStatisticsConstants.ROUTE_ID)
		.process(new KafkaProcessor())
		.setHeader(Exchange.HTTP_METHOD, simple(OrderStatisticsConstants.POST))
		.setHeader(Exchange.CONTENT_TYPE, constant(OrderStatisticsConstants.APPLICATION_JSON))
		.to(OrderStatisticsConstants.MONGO_DB_INSERT_URL)
		.process(new RestProcessor());


		

	}

}
