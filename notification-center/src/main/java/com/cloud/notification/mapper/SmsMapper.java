package com.cloud.notification.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.notification.SmsDTO;

@Mapper
public interface SmsMapper {
	
	@Insert("insert into sys_sms(phone, sign_name, template_code, params, day, create_time, update_time) "
			+ "values(#{smsDTO.phone}, #{smsDTO.signName}, #{smsDTO.templateCode}, #{smsDTO.params}, #{smsDTO.day}, #{smsDTO.createTime}, #{smsDTO.updateTime})")
	public int save(@Param("smsDTO") SmsDTO smsDTO);
	
	@Select("select * from sys_sms t where t.id = #{id}")
	public SmsDTO findById(Long id);
	
	public int update(SmsDTO smsDTO);
	
	public int count(Map<String, Object> params);
	
	public List<SmsDTO> findData(Map<String, Object> params);

}
