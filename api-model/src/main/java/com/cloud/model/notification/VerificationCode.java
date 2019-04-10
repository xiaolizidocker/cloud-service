package com.cloud.model.notification;

import java.io.Serializable;

import lombok.Data;

@Data
public class VerificationCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2214264765326834686L;
	
	private String key;

}
