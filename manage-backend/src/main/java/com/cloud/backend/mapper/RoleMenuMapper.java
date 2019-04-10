package com.cloud.backend.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cloud.backend.entity.Menu;
import com.cloud.backend.provider.RoleMenuProvider;

@Mapper
public interface RoleMenuMapper {
	
	@Insert("insert into sys_role_menu(roleId, menuId) values(#{roleId}, #{menuId})")
	public int save(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

	public int delete(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

	@Select("select t.menuId from sys_role_menu t where t.roleId = #{roleId}")
	public Set<Long> findMenuIdsByRoleId(Long roleId);
	
//	@Select({
//		"<script> "
//		+ "select m.id id, m.parent_id parentId, m.name name, m.css css, m.url url, m.sort sort from sys_menu m left join sys_role_menu rm on rm.menu_id = m.id where rm.role_id in "
//			+ "<foreach item = 'item' index = 'index' collection = 'roleIds' open = '(' separator = ',' close = ')' > "
//				+ "#{item} "
//			+ "</foreach> "
//		+ "</script>"
//	})
	public List<Menu> findMenusByRoleIds(@Param("roleIds") Set<Long> roleIds);
}
