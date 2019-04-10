package com.cloud.backend.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_menu")
public class Menu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3087193284343389081L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 22)
	private Long parentId;
	
	@Column(nullable = false, length = 64)
	private String name;
	
	@Column(length = 32)
	private String css;
	
	@Column(length = 1024)
	private String url;
	
	@Column(nullable = false, length = 11)
	private Integer sort;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Transient
	private List<Menu> child;

}
