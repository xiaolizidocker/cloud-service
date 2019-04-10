package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PermissionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7068781642907243548L;

	private Long id;
	
	private String permission;
	
	private String name;
	
	private Date createTime;
	
	private Date updateTime;
	
}
