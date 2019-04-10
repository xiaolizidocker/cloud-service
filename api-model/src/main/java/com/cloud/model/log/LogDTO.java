package com.cloud.model.log;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8117654589249045352L;
	
	private Long id;
	
	private String username;
	
	private String module;
	
	private String params;
	
	private String remark;
	
	private Integer flag;
	
	private Date createTime;
	
	

}
