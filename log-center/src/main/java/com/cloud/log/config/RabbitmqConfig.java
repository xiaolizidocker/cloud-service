package com.cloud.log.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.model.log.constants.LogQueue;

/**
 * 配置 rabbitmq
 * rabbitmq采用集群+HA代理+keepAlive高可用
 * 镜像队列以mirror.命名开头
 * @author zhouyu
 *
 */
@Configuration
public class RabbitmqConfig {
	/**
	 * 声明队列
	 * 
	 * @return
	 */
	@Bean
	public Queue logQueue() {
		Queue queue = new Queue(LogQueue.LOG_QUEUE);

		return queue;
	}
}
