package com.cloud.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_role_permission")
public class SysRolePermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7256137686803434160L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false)
	private Long roleId;
	
	@Column(length = 20, nullable = false)
	private Long permissionId;
	
}
