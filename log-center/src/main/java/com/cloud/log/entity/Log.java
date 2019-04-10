package com.cloud.log.entity;

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
@Table(name = "sys_log")
@Entity
public class Log implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1593811244542909986L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
		
	@Column(nullable = false, length = 50)
	private String username;
	
	@Column(nullable = false, length = 100)
	private String module;
	
	@Column(columnDefinition="TEXT")
	private String params;
	
	@Column(columnDefinition="TEXT")
	private String remark;
	
	/**
	 * 是否执行成功 1、成功；0、失败
	 */
	@Column(columnDefinition = "bit", length = 1, nullable = false)
	private Integer flag;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
