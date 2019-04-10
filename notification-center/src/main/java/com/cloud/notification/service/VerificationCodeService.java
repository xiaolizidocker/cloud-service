package com.cloud.notification.service;

import com.cloud.model.notification.VerificationCode;

public interface VerificationCodeService {
	
	/**
	 * 发送短信并且返回验证码
	 * @param phone
	 * @return
	 */
	public VerificationCode generateCode(String phone);
	
	public String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second);
}
