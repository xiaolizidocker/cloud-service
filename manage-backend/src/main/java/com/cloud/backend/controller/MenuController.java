package com.cloud.backend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.backend.entity.Menu;
import com.cloud.backend.service.MenuService;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.RoleDTO;

@RestController
@RequestMapping("/menus")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@GetMapping("/me")
	public List<Menu> findMyMenu(){
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Set<RoleDTO> roles = loginAppUser.getSysRoles();
		if(CollectionUtils.isEmpty(roles)) {
			return Collections.emptyList();
		}
		List<Menu> menus = menuService.findMenuByRoles(roles.parallelStream().map(RoleDTO::getId).collect(Collectors.toSet()));
		List<Menu> firstLevelMenus = menus.stream().filter(m -> m.getParentId().equals(0L))
				.collect(Collectors.toList());
		firstLevelMenus.forEach(m -> {
			setChild(m, menus);
		});
		return firstLevelMenus;
	}
	
	private void setChild(Menu menu, List<Menu> menus) {
		List<Menu> child = menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(child)) {
			menu.setChild(child);
			child.parallelStream().forEach(c -> {
				setChild(c, menus);
			});
		}
	}
	
	@PreAuthorize("hasAnyAuthority('back:menu:set2role','back:menu:query')")
	@GetMapping("/tree")
	public List<Menu> findMenuTree(){
		List<Menu> all = menuService.findAllMenus();
		List<Menu> list = new ArrayList<>();
		setMenuTree(0L, all, list);
		return list;
	}
	
	private void setMenuTree(Long parentId, List<Menu> all, List<Menu> list) {
		all.forEach(menu -> {
			if (parentId.equals(menu.getParentId())) {
				list.add(menu);

				List<Menu> child = new ArrayList<>();
				menu.setChild(child);
				setMenuTree(menu.getId(), all, child);
			}
		});
	}
	
	
	/**
	 * 获取角色的菜单
	 * 
	 * @param roleId
	 */
	@PreAuthorize("hasAnyAuthority('back:menu:set2role','menu:byroleid')")
	@GetMapping(params = "roleId")
	public Set<Long> findMenuIdsByRoleId(Long roleId) {
		return menuService.findMenuIdsByRoleId(roleId);
	}
	
	/**
	 * 添加菜单
	 * 
	 * @param menu
	 */
	@LogAnnotation(module = "添加菜单")
	@PreAuthorize("hasAuthority('back:menu:save')")
	@PostMapping
	public Menu save(@RequestBody Menu menu) {
		menuService.save(menu);

		return menu;
	}
	
	/**
	 * 修改菜单
	 * 
	 * @param menu
	 */
	@LogAnnotation(module = "修改菜单")
	@PreAuthorize("hasAuthority('back:menu:update')")
	@PutMapping
	public Menu update(@RequestBody Menu menu) {
		menuService.update(menu);

		return menu;
	}
	
	
	/**
	 * 删除菜单
	 * 
	 * @param id
	 */
	@LogAnnotation(module = "删除菜单")
	@PreAuthorize("hasAuthority('back:menu:delete')")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		menuService.delete(id);
	}

	/**
	 * 查询所有菜单
	 */
	@PreAuthorize("hasAuthority('back:menu:query')")
	@GetMapping("/all")
	public List<Menu> findAll() {
		List<Menu> all = menuService.findAllMenus();
		List<Menu> list = new ArrayList<>();
		setSortTable(0L, all, list);

		return list;
	}
	
	/**
	 * 菜单table
	 * 
	 * @param parentId
	 * @param all
	 * @param list
	 */
	private void setSortTable(Long parentId, List<Menu> all, List<Menu> list) {
		all.forEach(a -> {
			if (a.getParentId().equals(parentId)) {
				list.add(a);
				setSortTable(a.getId(), all, list);
			}
		});
	}
	
	@PreAuthorize("hasAuthority('back:menu:query')")
	@GetMapping("/{id}")
	public Menu findById(@PathVariable Long id) {
		return menuService.findMenuById(id);
	}

}
