package com.cloud.user.entity;

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
 * 用户实体类
 * @author zhouyu
 *
 */
@Data
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 611197991672067628L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String username;
	
	@Column(nullable = false, length = 60)
	private String password;
	
	@Column(length = 255)
	private String nickName;
	
	@Column(length = 1024)
	private String headImgUrl;

	@Column(length = 11)
	private String phone;
	
	/**
	 * 0：女、1：男
	 */
	@Column(columnDefinition = "bit", length = 1, nullable = false)
	private Integer sex;
	
	/**
	 * 状态 0:禁用、1：可用
	 */
	@Column(columnDefinition = "bit", length = 1, nullable = false)
	private Boolean enabled;
	
	@Column(length = 16)
	private String type;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

}
