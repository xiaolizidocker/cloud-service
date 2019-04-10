package com.cloud.log.config;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.log.LogDTO;
import com.cloud.model.log.constants.LogQueue;
import com.cloud.model.user.LoginAppUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogMqClient {
	
	private AmqpTemplate amqpTemplate;
	
	public LogMqClient(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}
	
	public void sendLog(String module, String username, String params, String remark, Integer flag) {
		LogDTO logDTO = new LogDTO();
		logDTO.setCreateTime(new Date());
		if(StringUtils.isNotBlank(username)) {
			logDTO.setUsername(username);
		}else {
				LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
				if(null != loginAppUser) {
					logDTO.setUsername(loginAppUser.getUsername());
				}
		}
		logDTO.setFlag(flag);
		logDTO.setModule(module);
		logDTO.setParams(params);
		logDTO.setRemark(remark);
		amqpTemplate.convertAndSend(LogQueue.LOG_QUEUE, logDTO);
		
		log.info("发送日志到队列：{}", JSONObject.toJSONString(logDTO));
		
	}

}
