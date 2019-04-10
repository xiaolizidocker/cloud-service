package com.cloud.model.common;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *返回前端的分页对象 
 * @author zhouyu
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -6344431186848075300L;
	
	private int total;
	
	private List<T> data;

}
