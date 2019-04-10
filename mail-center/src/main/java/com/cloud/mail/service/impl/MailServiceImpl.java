package com.cloud.mail.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.PageUtil;
import com.cloud.mail.mapper.MailMapper;
import com.cloud.model.common.Page;
import com.cloud.model.mail.MailDTO;
import com.cloud.model.mail.constants.MailStatus;
import com.cloud.model.mail.service.MailService;
import com.cloud.model.user.LoginAppUser;

import lombok.extern.slf4j.Slf4j;

/**
 * 注册dubbo服务
 * @author zhouyu
 *
 */
@Service(version = "2.0.0", interfaceClass = MailService.class)
@Slf4j
@Component
public class MailServiceImpl implements MailService{
	
	@Autowired
	private MailMapper mailMapper;
	
	@Autowired
	private MailServer mailServer;

	/**
	 * 保存邮件
	 */
	@Override
	public void saveMail(MailDTO mailDTO) {
		if(null == mailDTO || StringUtils.isBlank(mailDTO.getUsername())) {
			LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
			if(null != loginAppUser) {
				mailDTO.setUserId(loginAppUser.getId());
				mailDTO.setUsername(loginAppUser.getUsername());
			}
		}
		if(null == mailDTO.getUserId()) {
			mailDTO.setUserId(0L);
			mailDTO.setUsername("系统邮件");
		}
		if(null == mailDTO.getCreateTime()) {
			mailDTO.setCreateTime(new Date());
		}
		mailDTO.setUpdateTime(new Date());
		mailDTO.setStatus(MailStatus.DRAFT);
		
		mailMapper.save(mailDTO);
		
		log.info("保存邮件：{}", JSONObject.toJSONString(mailDTO));
	}

	/**
	 * 编辑未发送邮件（包含未发送成功的）
	 */
	@Override
	public void updateMail(MailDTO mailDTO) {
		MailDTO olMailDTO = mailMapper.findById(mailDTO.getId());
		if(olMailDTO.getStatus() == MailStatus.SUCCESS) {
			throw new IllegalArgumentException("已经发送成功的邮件不支持编辑！");
		}
		mailDTO.setUpdateTime(new Date());
		
		mailMapper.update(mailDTO);
		
		log.info("修改邮件：{}", JSONObject.toJSONString(mailDTO));
	}

	/**
	 * 发送邮件并更新邮件发送结果
	 */
	@Override
	public void sendMail(MailDTO mailDTO) {
		if(null == mailDTO) {
			log.info("邮件为空！");
			return;
		}
		boolean flag = mailServer.sendMail(mailDTO.getToEmail(), mailDTO.getSubject(), mailDTO.getContent());
		mailDTO.setSendTime(new Date());
		mailDTO.setStatus(flag ? MailStatus.SUCCESS : MailStatus.ERROR);
		
		mailMapper.update(mailDTO);
	}

	@Override
	public MailDTO findById(Long id) {
		return mailMapper.findById(id);
	}

	@Override
	public Page<MailDTO> findMails(Map<String, Object> params) {
		int total = mailMapper.count(params);
		
		List<MailDTO> list = Collections.emptyList();
		if(total > 0) {
			PageUtil.pageParamConver(params, true);
			list = mailMapper.findData(params);
		}
		return new Page<>(total, list);
	}

}
