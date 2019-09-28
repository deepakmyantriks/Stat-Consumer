package com.yantriks.statistics.consumer.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;



@Configuration
@ComponentScan(basePackages = "com.order.statistics.consumer")
public class MongoDBConfig {
	
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.data.mongodb.database}")
	private String database;
	
	@Value("${spring.data.mongodb.collecton-name}")
	private String collection;
	
	@Value("${spring.data.mongodb.host}")
	private String hostname;
	
	@Value("${spring.data.mongodb.port}")
	private String port;
	
	@Value("${spring.data.mongodb.username}")
	private String user;
	
	@Value("${spring.data.mongodb.password}")
	private String password;
	
	@Value("${spring.data.mongo.sort.key}")
	private String sortingKey;
	

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		
		logger.debug("user: "+ user);
		logger.debug("password: "+ password);
		logger.debug("database: "+ database);

		logger.debug("hostname: "+ hostname);
		logger.debug("port: "+ port);
		logger.debug("collection: "+collection);

		List<MongoCredential> credentials = new ArrayList<>();
		
		if (user != null && password != null && !user.trim().isEmpty() && !password.trim().isEmpty()) {
			credentials.add(MongoCredential.createCredential(user, database, password.toCharArray()));
		}
		
		//MongoCredential credential = MongoCredential.createCredential(user, database,password.toCharArray());
	    ServerAddress serverAddress = new ServerAddress(hostname, Integer.parseInt(port));
	   
	    // Mongo Client
	    
		@SuppressWarnings("deprecation")
		MongoClient mongoClient = new MongoClient(serverAddress,credentials); 
	
		
	    // Mongo DB Factory
	    return new SimpleMongoDbFactory(mongoClient, database);
	
		
	}

	public String getDatabase() {
		return database;
	}

	public String getCollection() {
		return collection;
	}
	public String getSortingKey() {
		return sortingKey;
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}
}
