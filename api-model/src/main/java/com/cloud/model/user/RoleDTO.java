package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class RoleDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5007457028644671156L;
	
	private Long id;
	private String code;
	private String name;
	private Date createTime;
	private Date updateTime;

}
