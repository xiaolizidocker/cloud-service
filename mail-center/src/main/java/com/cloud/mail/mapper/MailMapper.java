package com.cloud.mail.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.mail.MailDTO;

@Mapper
public interface MailMapper {
	
	@Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_mail(user_id, username, to_email, subject, content, status, create_time, update_time) values(#{userId}, #{username}, #{toEmail}, #{subject}, #{content}, #{status}, #{createTime}, #{updateTime})")
    int save(MailDTO mailDTO);

    int update(MailDTO mailDTO);

    @Select("select * from sys_mail t where t.id = #{id}")
    MailDTO findById(Long id);

    int count(Map<String, Object> params);

    List<MailDTO> findData(Map<String, Object> params);

}
