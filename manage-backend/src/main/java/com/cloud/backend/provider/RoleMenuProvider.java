package com.cloud.backend.provider;
import org.apache.ibatis.annotations.Param;


public class RoleMenuProvider {
	
	public String delete(@Param("roleId") Long roleId, @Param("menuId") Long menuId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from sys_role_menu where 1=1 ");
		if(null != roleId) {
			sql.append("and role_id = #{roleId} ");
		}
		if(null != menuId) {
			sql.append("and menu_id = #{menuId}");
		}
		return sql.toString();
	}
}
