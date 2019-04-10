package com.cloud.user.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.model.user.PermissionDTO;

@Mapper
public interface RolePermissionMapper {
	
	@Insert("insert into sys_role_permission(roleId, permissionId) values(#{roleId}, #{permissionId})")
	public int saveRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

	public int deleteRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

	public Set<PermissionDTO> findPermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);
}
