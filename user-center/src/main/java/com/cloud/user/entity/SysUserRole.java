package com.cloud.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户角色关系表
 * @author zhouyu
 *
 */
@Data
@Entity
@Table(name = "sys_user_role")
public class SysUserRole implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 129521255916450468L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false)
	private Long userId;
	
	@Column(length = 20, nullable = false)
	private Long roleId;

}
