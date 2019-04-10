package com.cloud.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 角色菜单对应关系
 * @author zhouyu
 *
 */
@Entity
@Table(name ="sys_role_menu")
@Data
public class RoleMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4862813476829399192L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 22)
	private Long roleId;
	
	@Column(nullable = false, length = 22)
	private Long menuId;

}
