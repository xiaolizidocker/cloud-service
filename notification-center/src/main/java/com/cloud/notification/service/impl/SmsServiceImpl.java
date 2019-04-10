package com.cloud.notification.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.common.Page;
import com.cloud.model.notification.SmsDTO;
import com.cloud.notification.mapper.SmsMapper;
import com.cloud.notification.service.SmsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService{
	
	@Autowired
	private IAcsClient iAcsClient;
	
	@Value("${aliyun.sign.name}")
	private String signName;
	
	@Value("${aliyun.template.code}")
	private String templateCode;
	
	@Autowired
	private SmsMapper smsMapper;

	@Override
	public void save(SmsDTO smsDTO, Map<String, String> params) {
		if(!CollectionUtils.isEmpty(params)) {
			smsDTO.setParams(JSONObject.toJSONString(params));
		}
		
		smsDTO.setCreateTime(new Date());
		smsDTO.setUpdateTime(new Date());
		smsDTO.setDay(new Date());
		smsMapper.save(smsDTO);
	}

	@Override
	public void update(SmsDTO smsDTO) {
		smsDTO.setUpdateTime(new Date());
		
		smsMapper.update(smsDTO);
	}

	@Override
	public SmsDTO findById(Long id) {
		return smsMapper.findById(id);
	}

	@Override
	public SendSmsResponse sendSmsMsg(SmsDTO smsDTO) {
		if(null == smsDTO.getSignName()) {
			smsDTO.setSignName(signName);
		}
		if(null == smsDTO.getTemplateCode()) {
			smsDTO.setTemplateCode(templateCode);
		}
		
		// sdk demo
		SendSmsRequest request = new SendSmsRequest();
		request.setMethod(MethodType.POST);
		request.setPhoneNumbers(smsDTO.getPhone());
		request.setSignName(smsDTO.getSignName());
		request.setTemplateCode(smsDTO.getTemplateCode());
		request.setTemplateParam(smsDTO.getParams());
		request.setOutId(UUID.randomUUID().toString());

		SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = iAcsClient.getAcsResponse(request);
			if(null != sendSmsResponse) {
				log.info("发送短信结果：code：{}，message：{}，requestId：{}， bizId：{}", sendSmsResponse.getCode(), sendSmsResponse.getMessage(), sendSmsResponse.getRequestId(), sendSmsResponse.getBizId());
				smsDTO.setCode(sendSmsResponse.getCode());
				smsDTO.setMessage(sendSmsResponse.getMessage());
				smsDTO.setBizId(sendSmsResponse.getBizId());
			}
		} catch (Exception e) {
			log.error("发送短信异常！", e);
		}
		update(smsDTO);
		return sendSmsResponse;
	}

	@Override
	public Page<SmsDTO> findSms(Map<String, Object> params) {
		int total = smsMapper.count(params);
		List<SmsDTO> list = Collections.emptyList();
		if (total > 0) {
			PageUtil.pageParamConver(params, true);

			list = smsMapper.findData(params);
		}
		return new Page<>(total, list);
	}

}
