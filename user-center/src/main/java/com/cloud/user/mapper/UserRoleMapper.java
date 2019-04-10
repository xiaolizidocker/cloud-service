package com.cloud.user.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.user.RoleDTO;

@Mapper
public interface UserRoleMapper {
	
	@Select("select r.* from sys_user_role ur left join sys_role r on r.id = ur.role_id where ur.user_id = #{userId} ")
	public Set<RoleDTO> findRolesByUserId(Long userId);

	public int deleteUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

	@Insert("insert into sys_user_role(user_id, role_id) values(#{userId}, #{roleId})")
	public int saveUserRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);
	
}
