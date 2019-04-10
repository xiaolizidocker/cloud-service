package com.cloud.mail.service.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 邮件发送
 * @author zhouyu
 *
 */
@Slf4j
@Component
public class MailServer {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	/**
	 * 发件人地址，既是配置的邮箱服务的邮箱
	 * 从apollo配置中心获取
	 */
	@Value("${spring.mail.username:13294157562@163.com}")
	private String serverMail;
	
	public boolean sendMail(String toMail, String subject, String content) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(serverMail);
			helper.setTo(toMail);
			helper.setSubject(subject);
			helper.setText(content);
			
			javaMailSender.send(message);
			
			log.info("发送邮件到：{}，主题：{}，内容：{}", toMail, subject, content);
		} catch (Exception e) {
			log.error("发送邮件失败！", e);;
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
}
