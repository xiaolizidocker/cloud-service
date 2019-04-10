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
 * 角色实体类
 * @author zhouyu
 *
 */
@Data
@Entity
@Table(name = "sys_role")
public class SysRole  implements Serializable {

	private static final long serialVersionUID = -2054359538140713354L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 32, unique = true)
	private String code;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
}
