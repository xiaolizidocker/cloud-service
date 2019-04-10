package com.cloud.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.user.UserDTO;
import com.cloud.user.entity.SysUser;

@Mapper
public interface UserMapper {
	
	@Select("select * from sys_user u where u.username = #{username}")
	public SysUser findByUsername(String username);
	
	@Select("select * from sys_user u where u.id = #{id}")
	public SysUser findById(Long id);
	
	public int count(Map<String, Object> params);
	
	public int update(UserDTO userDTO);
	
	public List<UserDTO> findData(Map<String, Object> params);
	
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_user(username, password, nick_name, head_img_url, phone, sex, enabled, type, create_time, update_time) "
			+ "values(#{userDTO.username}, #{userDTO.password}, #{userDTO.nickName}, #{userDTO.headImgUrl}, #{userDTO.phone}, #{userDTO.sex},"
			+ " #{userDTO.enabled}, #{userDTO.type}, #{userDTO.createTime}, #{userDTO.updateTime})")
	public int save(@Param("userDTO") UserDTO userDTO);
}
