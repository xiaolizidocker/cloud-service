package com.cloud.mail.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_mail")
public class Mail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8945122740389698241L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false)
	private Long userId;
	
	/**
	 * 发件人姓名
	 */
	@Column(length = 50, nullable = false)
	private String username;
	
	/**
	 * 收件人地址
	 */
	@Column(length = 128, nullable = false)
	private String toEmail;
	
	/**
	 * 标题
	 */
	@Column(length = 255, nullable = false)
	private String subject;
	
	/**
	 * 正文
	 */
	@Column(columnDefinition="TEXT")
	private String content;
	
	/**
	 * 发送状态
	 * @see com.cloud.model.mail.constants.MailStatus
	 */
	@Column(columnDefinition = "INT", length = 1, nullable = false)
	private Integer status;
	
	/**
	 * 发送时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendTime;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

}
