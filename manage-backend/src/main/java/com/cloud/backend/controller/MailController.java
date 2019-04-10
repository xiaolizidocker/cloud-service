package com.cloud.backend.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.mail.MailDTO;
import com.cloud.model.mail.service.MailService;

@RestController
@RequestMapping("/mails")
public class MailController {
	
	@Reference(version = "2.0.0")
	private MailService mailService;
	
	@PreAuthorize("hasAuthority('mail:query')")
	@GetMapping("/{id}")
	public MailDTO findById(@PathVariable Long id) {
		return mailService.findById(id);
	}
	
	@PreAuthorize("hasAuthority('mail:query')")
    @GetMapping
	public Page<MailDTO> findMails(@RequestParam Map<String, Object> params){
		return mailService.findMails(params);
	}
	
	
	@LogAnnotation(module = "保存邮件")
    @PreAuthorize("hasAuthority('mail:save')")
    @PostMapping
	public MailDTO save(@RequestBody MailDTO mailDTO, Boolean send) {
		mailService.saveMail(mailDTO);
		if(Boolean.TRUE == send) {
			mailService.sendMail(mailDTO);
		}
		return mailDTO;
	}
	
	
	@LogAnnotation(module = "修改邮件")
    @PreAuthorize("hasAuthority('mail:update')")
    @PutMapping
	public MailDTO update(@RequestBody MailDTO mailDTO, Boolean send) {
		mailService.updateMail(mailDTO);
		if(Boolean.TRUE == send) {
			mailService.sendMail(mailDTO);
		}
		return mailDTO;
	}
	
}
