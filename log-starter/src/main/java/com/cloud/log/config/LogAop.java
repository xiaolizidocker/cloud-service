package com.cloud.log.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.LogDTO;
import com.cloud.model.log.constants.LogQueue;
import com.cloud.model.user.LoginAppUser;

import lombok.extern.slf4j.Slf4j;

/**
 * aop实现日志
 * @author zhouyu
 * 自动装配
 */
@Aspect
@Slf4j
public class LogAop {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	/**
	 * 环绕，连接点与切面
	 * @param joinPoint
	 */
	@Around(value = "@annotation(com.cloud.model.log.LogAnnotation)")
	public void logSave(ProceedingJoinPoint joinPoint) throws Throwable{
		LogDTO logDTO = new LogDTO();
		logDTO.setCreateTime(new Date());
		
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if(null != loginAppUser) {
			logDTO.setUsername(loginAppUser.getUsername());
		}
		
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
		logDTO.setModule(logAnnotation.module());
		
		// 如果记录参数
		if(logAnnotation.recordParam()) {
			// 获取参数名
			String[] paramsName = methodSignature.getParameterNames();
			if(null != paramsName && paramsName.length > 0) {
				// 获取参数值
				Object[] args = joinPoint.getArgs();
				
				Map<String, Object> params = new HashMap<>();
				for(int i = 0; i < paramsName.length; i++) {
					Object value = args[i];
					if(value instanceof Serializable) {
						params.put(paramsName[i], value);
					}
				}
				logDTO.setParams(JSONObject.toJSONString(params));
				try {
					// 方法执行
					Object object = joinPoint.proceed();
					logDTO.setFlag(1);
				} catch (Exception e) {
					logDTO.setFlag(0);;
					logDTO.setRemark(e.getMessage());
					throw e;
				}finally {
					amqpTemplate.convertAndSend(LogQueue.LOG_QUEUE, logDTO);
					log.info("发送日志到了队列：{}", JSONObject.toJSON(logDTO));
				}
			}
		}
	}
}
