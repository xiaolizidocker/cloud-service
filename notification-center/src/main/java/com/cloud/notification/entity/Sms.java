package com.cloud.notification.entity;

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

/**
 * 短信实体类
 * @author zhouyu
 *
 */
@Data
@Entity
@Table(name = "sys_sms")
public class Sms implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1306835086876673121L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 16, nullable = false)
	private String phone;
	
	/**
	 * 阿里云短信签名
	 */
	@Column(length = 128)
	private String signName;
	
	/**
	 * 阿里云模板code
	 */
	@Column(length = 128)
	private String templateCode;
	
	/**
	 * 短信参数
	 */
	@Column(length = 500)
	private String params;
	
	/**
	 * 阿里云短信发送接口返回的业务id
	 */
	@Column(length = 128)
	private String bizId;
	
	/**
	 * 发送短信返回的状态码
	 */
	@Column(length = 64)
	private String code;
	
	/**
	 * 短信体
	 */
	@Column(length = 128)
	private String message;
	
	/**
	 * 冗余字段,便于统计某天的发送次数
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date day;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

}
