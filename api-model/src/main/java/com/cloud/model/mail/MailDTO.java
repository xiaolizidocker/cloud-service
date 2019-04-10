package com.cloud.model.mail;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 邮件
 * @author zhouyu
 *
 */
@Data
public class MailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7956179709962864431L;
	
	private Long id;
	
	private Long userId;
	
	/**
	 * 发件人姓名
	 */
	private String username;
	
	/**
	 * 收件人地址
	 */
	private String toEmail;
	
	/**
	 * 标题
	 */
	private String subject;
	
	/**
	 * 正文
	 */
	private String content;
	
	/**
	 * 发送状态
	 * @see com.cloud.model.mail.constants.MailStatus
	 */
	private Integer status;
	
	/**
	 * 发送时间
	 */
	private Date sendTime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
