package com.cloud.notification.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.cloud.model.notification.SmsDTO;
import com.cloud.model.notification.VerificationCode;
import com.cloud.model.redis.service.RedisService;
import com.cloud.notification.service.SmsService;
import com.cloud.notification.service.VerificationCodeService;
import com.cloud.notification.util.SmsUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 验证码校验
 * 首先生成6位数验证码，并生成一个uuid，以uuid作为key将验证码和手机号存入redis，将uuid返回给前端。
 * 前端校验验证码通过将uuid和验证码发送给后端
 * @author zhouyu
 *
 */
@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService{
	
	@Value("${sms.expire-minute:10}")
	private Integer expireMinute;
	
	@Value("${sms.day-count:30}")
	private Integer dayCount;
	
	@Reference(version = "2.0.0")
	private RedisService redisService;
	
	@Autowired
	private SmsService smsService;
	
	@Override
	public VerificationCode generateCode(String phone) {
		String uuid = UUID.randomUUID().toString();
		String code = SmsUtils.randomCode(6);
		
		Map<String, String> map = new HashMap<>(2);
		map.put("code", code);
		map.put("phone", phone);
		
		redisService.set(generateSmsRedisKey(uuid), JSONObject.toJSONString(map), expireMinute * 60);
		log.info("缓存验证码：{}", JSONObject.toJSONString(map));
		
		saveSmsAndSendCode(phone, code);
		
		VerificationCode verificationCode  = new VerificationCode();
		verificationCode.setKey(uuid);
		return verificationCode;
	}
	
	/**
	 * 保存短信并且发送短信验证码
	 * @param phone
	 * @param code
	 */
	private void saveSmsAndSendCode(String phone, String code) {
		checkTodaySendCount(phone);
		
		SmsDTO smsDTO = new SmsDTO();
		smsDTO.setPhone(phone);
		
		Map<String, String> params = new HashMap<>();
		params.put("code", code);
		smsService.save(smsDTO, params);
		
		smsService.sendSmsMsg(smsDTO);
		
		String key = generateSmsRedisKey("count:"+LocalDate.now().toString()+":"+phone);
		Object value = redisService.get(key);
		if(null != value) {
			Integer count = Integer.parseInt((String)value);
			redisService.set(key, count + 1);
		}else {
			redisService.set(key, 1);
		}
		redisService.expire(key, 24*60*60);
		
	}
	
	/**
	 * 检查今天单个号码发送短信数量是否超标
	 * @param phone
	 */
	private void checkTodaySendCount(String phone) {
		String key = generateSmsRedisKey("count:"+LocalDate.now().toString()+":"+phone);
		String value = (String) redisService.get(key);
		if(null != value) {
			Integer count = Integer.parseInt(value);
			if(count > dayCount) {
				throw new IllegalArgumentException("短信发送次数已超过当前先知！");
			}
		}
	}

	@Override
	public String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second) {
		key = generateSmsRedisKey(key);
		
		String value = (String) redisService.get(key);
		if(null != value) {
			JSONObject jsonObject = JSONObject.parseObject(value);
			if(code != null && code.equals(jsonObject.getString("code"))) {
				log.info("验证码校验成功：{}", code);
				
				if(delete == null || delete) {
					redisService.delete(key);
				}
				
				if (delete == Boolean.FALSE && second != null && second > 0) {
					redisService.expire(key, second);
				}
				
				return jsonObject.getString("phone");
			}
		}
		return null;
	}
	
	/**
	 * 给 sms的redis添加key前缀
	 * @param key
	 * @return
	 */
	private String generateSmsRedisKey(String key) {
		return "sms:"+key;
	}

}
