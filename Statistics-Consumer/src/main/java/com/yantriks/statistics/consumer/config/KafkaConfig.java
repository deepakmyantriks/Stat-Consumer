package com.yantriks.statistics.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;




@Configuration
@ComponentScan(basePackages = "com.oms.statistics.consumer")
public class KafkaConfig {
	

	@Value("${kafka.topic}")
	private String kafkaTopic;
	
	@Value("${kafka.server.url}")
	private String kafkaUrl;
	
	@Value("${kafka.server.port}")
	private String kafkaPort;

	@Value("${kafka.autoOffsetReset}")
	private String autoOffsetReset;
	
	@Value("${kafka.groupId}")
	private String groupId;

	
	
	public String getGroupId() {
		return groupId;
	}

	public String getAutoOffsetReset() {
		return autoOffsetReset;
	}

	public String getKafkaTopic() {
		return kafkaTopic;
	}

	public String getKafkaUrl() {
		return kafkaUrl;
	}

	public String getKafkaPort() {
		return kafkaPort;
	}
	
	
}
