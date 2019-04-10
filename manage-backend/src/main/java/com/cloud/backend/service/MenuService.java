package com.cloud.backend.service;

import java.util.List;
import java.util.Set;

import com.cloud.backend.entity.Menu;

public interface MenuService {
	
	public void save(Menu menu);
	
	public void update(Menu menu);
	
	public void delete(Long id);
	
	public void setMenuToRole(Long roleId, Set<Long> menuIds);
	
	public List<Menu> findMenuByRoles(Set<Long> roleId);
	
	public List<Menu> findAllMenus();
	
	public Menu findMenuById(Long id);
	
	public Set<Long> findMenuIdsByRoleId(Long roleId);

}
