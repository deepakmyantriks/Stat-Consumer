package com.yantriks.statistics.consumer.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestProcessor implements Processor {

	 private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void process(Exchange exchange) throws Exception {
    	logger.debug("RestProcessor");

    	logger.debug("Response: \n"+exchange.getIn().getBody(String.class));
    }

}