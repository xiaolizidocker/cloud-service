package com.cloud.file.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "sys_file_info")
@Data
public class FileInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length = 32)
	private String id;
	
	@Column(length = 128, nullable = false)
	private String name;
	
	@Column(columnDefinition = "bit", length = 1, nullable = false)
	private Integer isImg;
	
	/**
	 * 文件类型
	 */
	@Column(length = 128, nullable = false)
	private String contentType;
	
	@Column(length = 11, nullable = false)
	private long size;
	
	@Column(length = 255)
	private String path;
	
	@Column(length = 1024, nullable = false)
	private String url;
	
	/**
	 * 文件存储地方
	 */
	@Column(length = 32, nullable = false)
	private String source;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	

}
