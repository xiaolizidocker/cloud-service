package com.cloud.backend.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cloud.backend.entity.Menu;
import com.cloud.backend.mapper.MenuMapper;
import com.cloud.backend.mapper.RoleMenuMapper;
import com.cloud.backend.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Override
	public void save(Menu menu) {
		menu.setCreateTime(new Date());
		menu.setUpdateTime(menu.getUpdateTime());
		menuMapper.save(menu);
		log.info("新增菜单：{}", JSONObject.toJSONString(menu));
	}

	@Override
	public void update(Menu menu) {
		menu.setUpdateTime(new Date());
		menuMapper.update(menu);
		log.info("修改菜单：{}", JSONObject.toJSONString(menu));
	}

	@Override
	public void delete(Long id) {
		Menu menu = menuMapper.findMenuById(id);
		menuMapper.deleteByParentId(id);
		menuMapper.delete(id);
		log.info("删除菜单：{}", JSONObject.toJSONString(menu));
	}

	@Override
	public void setMenuToRole(Long roleId, Set<Long> menuIds) {
		// 先删除掉role下的所有角色在添加
		roleMenuMapper.delete(roleId, null);
		if(CollectionUtils.isNotEmpty(menuIds)) {
			menuIds.forEach(menuId -> {
				roleMenuMapper.save(roleId, menuId);
			});
		}
	}

	@Override
	public List<Menu> findMenuByRoles(Set<Long> roleIds) {
		return roleMenuMapper.findMenusByRoleIds(roleIds);
	}

	@Override
	public List<Menu> findAllMenus() {
		return menuMapper.findAllMenus();
	}

	@Override
	public Menu findMenuById(Long id) {
		return menuMapper.findMenuById(id);
	}

	@Override
	public Set<Long> findMenuIdsByRoleId(Long roleId) {
		return roleMenuMapper.findMenuIdsByRoleId(roleId);
	}

}
