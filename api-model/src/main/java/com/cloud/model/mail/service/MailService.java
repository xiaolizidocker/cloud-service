package com.cloud.model.mail.service;

import java.util.Map;

import com.cloud.model.common.Page;
import com.cloud.model.mail.MailDTO;

/**
 * mail dubbo接口
 * @author zhouyu
 *
 */
public interface MailService {
	
	public void saveMail(MailDTO mailDTO);
	
	public void updateMail(MailDTO mailDTO);
	
	public void sendMail(MailDTO mailDTO);
	
	public MailDTO findById(Long id);
	
	public Page<MailDTO> findMails(Map<String, Object> params);
}
