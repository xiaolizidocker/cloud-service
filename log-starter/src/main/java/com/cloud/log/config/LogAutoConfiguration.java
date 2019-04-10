package com.cloud.log.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.model.log.constants.LogQueue;

/**
 * 自动装配日志starter需要的bean
 * @author zhouyu
 *
 */
@Configuration
public class LogAutoConfiguration {
	
	@Bean
	public Queue logQueue() {
		return new Queue(LogQueue.LOG_QUEUE);
	}
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Bean
	public LogMqClient logMqClient() {
		return new LogMqClient(amqpTemplate);
	}
}
