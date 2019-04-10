package com.cloud.backend.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
public class BlackIP implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip;
	
	private Date createTime;

}
