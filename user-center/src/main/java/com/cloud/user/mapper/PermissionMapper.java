package com.cloud.user.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cloud.model.user.PermissionDTO;
import com.cloud.user.entity.SysPermission;

@Mapper
public interface PermissionMapper {
	
	@Select({
		"<script> "
		+ "select p.* from sys_role_permission rp left join sys_permission p on p.id = rp.permission_id where rp.role_id in "
			+ "<foreach item = 'item' index = 'index' collection = 'roleIds' open = '(' separator = ',' close = ')' > "
				+ "#{item} "
			+ "</foreach> "
		+ "</script>"
	})
	public Set<SysPermission> findByRoleIds(@Param("roleIds") Set<Long> roleIds);
	
	
	@Select("select * from sys_permission t where t.permission = #{permission}")
	public PermissionDTO findByPermission(String permission);

	public int count(Map<String, Object> params);

	public List<PermissionDTO> findData(Map<String, Object> params);
	
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_permission(permission, name, create_time, update_time) values(#{permission}, #{name}, #{createTime}, #{createTime})")
	public int save(PermissionDTO permissionDTO);

	@Update("update sys_permission t set t.name = #{name}, t.permission = #{permission}, t.update_time = #{updateTime} where t.id = #{id}")
	public int update(PermissionDTO permissionDTO);

	@Delete("delete from sys_permission where id = #{id}")
	public int delete(Long id);
	
	@Select("select * from sys_permission t where t.id = #{id}")
	public PermissionDTO findById(Long id);
}
