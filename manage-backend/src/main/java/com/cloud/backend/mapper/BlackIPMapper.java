package com.cloud.backend.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cloud.backend.entity.BlackIP;

@Mapper
public interface BlackIPMapper {

	@Insert("insert into sys_black_ip(ip, create_time) values(#{ip}, #{createTime})")
	public int save(BlackIP blackIP);

	@Delete("delete from sys_black_ip where ip = #{ip}")
	public int delete(String ip);

	@Select("select * from sys_black_ip t where t.ip = #{ip}")
	public BlackIP findByIp(String ip);

	public int count(Map<String, Object> params);

	public List<BlackIP> findData(Map<String, Object> params);
}
