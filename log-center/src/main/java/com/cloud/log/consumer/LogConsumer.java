package com.cloud.log.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cloud.log.service.LogService;
import com.cloud.model.log.LogDTO;
import com.cloud.model.log.constants.LogQueue;

import lombok.extern.slf4j.Slf4j;

@Component
@RabbitListener(queues = LogQueue.LOG_QUEUE)
@Slf4j
public class LogConsumer {
	
	@Autowired
	private LogService logService;
	
	@RabbitHandler
	public void logHandler(LogDTO logDTO) {
		try {
			logService.save(logDTO);
		} catch (Exception e) {
			log.error("保存日志失败，日志：{}，异常：{}", JSONObject.toJSONString(logDTO), e);
		}
	}
	
}
