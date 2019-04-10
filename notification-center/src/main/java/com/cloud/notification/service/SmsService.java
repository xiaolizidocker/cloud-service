package com.cloud.notification.service;

import java.util.Map;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cloud.model.common.Page;
import com.cloud.model.notification.SmsDTO;

public interface SmsService {
	
	public void save(SmsDTO smsDTO, Map<String, String> params);
	
	public void update(SmsDTO smsDTO);
	
	public SmsDTO findById(Long id);
	
	public Page<SmsDTO> findSms(Map<String, Object> params);
	
	/**
	 * 发送短信
	 * @param smsDTO
	 * @return
	 */
	public SendSmsResponse sendSmsMsg(SmsDTO smsDTO);

}
