package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import lombok.Data;

@Data
public class UserDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3870050153147040453L;

	private Long id;
	private String username;
	private String password;
	private String nickName;
	private String headImgUrl;
	private String phone;
	private Integer sex;
	/**
	 * 状态
	 */
	private Boolean enabled;
	private String type;
	private Date createTime;
	private Date updateTime;
	
	private Set<RoleDTO> sysRoles;

	private Set<String> permissions;

}
