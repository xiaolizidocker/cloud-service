package com.cloud.log.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.model.log.LogDTO;

@Mapper
public interface LogMapper {
	
	@Insert("insert into sys_log(username, module, params, remark, flag, create_time) values(#{logDTO.username}, "
			+ "#{logDTO.module}, #{logDTO.params}, #{logDTO.remark}, "
			+ "#{logDTO.flag}, #{logDTO.createTime})")
	public int save(@Param("logDTO") LogDTO logDTO);
	
	public int count(Map<String, Object> params);
	
	public List<LogDTO> findData(Map<String, Object> params);

}
