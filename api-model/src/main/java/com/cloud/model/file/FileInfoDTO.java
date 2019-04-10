package com.cloud.model.file;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 文件信息
 * @author zhouyu
 *
 */
@Data
public class FileInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6809925811759383342L;
	
	/** 文件的md5 */
	private String id;
	
	/** 原始文件名 */
	private String name;
	
	/** 是否是图片 */
	private Integer isImg;
	
	/**
	 * 文件类型
	 */
	private String contentType;
	
	private long size;
	
	private String path;
	
	private String url;
	
	/**
	 * 文件来源
	 * 
	 * @see FileSource
	 */
	private String source;
	
	private Date createTime;

}
