package com.cloud.user.controller;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.RoleDTO;
import com.cloud.model.user.UserDTO;
import com.cloud.user.feign.SmsClient;
import com.cloud.user.service.UserService;

@RestController
public class UserController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SmsClient smsClient;
	
	
	@GetMapping(value = "/users-anon/internal", params = "username")
	public LoginAppUser findByUsername(@RequestParam("username") String username) {
		return userService.findByUsername(username);
	}
	
	@PreAuthorize("hasAuthority('back:user:query')")
	@GetMapping("/users/{id}")
	public LoginAppUser findUserByUserId(@PathVariable Long id) {
		return userService.findById(id);
	}
	

    /**
     * 当前登录用户 LoginAppUser
     */
    @GetMapping("/users/current")
    public LoginAppUser getLoginAppUser() {
        return AppUserUtil.getLoginAppUser();
    }
    
    /**
     * 用户查询
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users")
    public Page<UserDTO> findUsers(@RequestParam Map<String, Object> params){
    	return userService.findUsers(params);
    }
    
    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    @PostMapping("/users-anon/register")
    public UserDTO register(@RequestBody UserDTO userDTO) {
    	userService.addSysUser(userDTO);
    	return userDTO;
    }
    
    /**
     * 修改个人信息
     * @param userDTO
     * @return
     */
    @LogAnnotation(module = "修改个人信息")
    @PutMapping("/users/me")
    public UserDTO updateMe(UserDTO userDTO) {
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	
    	userDTO.setId(loginAppUser.getId());
    	
    	userService.updateUser(userDTO);
    	return userDTO;
    }
    
    
    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     */
    @LogAnnotation(module = "修改密码")
    @PutMapping(value = "/users/password", params = {"oldPassword", "newPassword"})
    public void updatePassword(String oldPassword, String newPassword) {
    	if(StringUtils.isBlank(oldPassword)) {
    		throw new IllegalArgumentException("旧密码不能为空！");
    	}
    	if(StringUtils.isBlank(newPassword)) {
    		throw new IllegalArgumentException("新密码不能为空！");
    	}
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	userService.updatePassword(loginAppUser.getId(), oldPassword, newPassword);
    }
    
    /**
     * 管理后台，给用户重置密码
     *
     * @param id          用户id
     * @param newPassword 新密码
     */
    @LogAnnotation(module = "重置密码")
    @PreAuthorize("hasAuthority('back:user:password')")
    @PutMapping(value = "/users/{id}/password", params = {"newPassword"})
    public void resetPassword(@PathVariable Long id, String newPassword) {
        userService.updatePassword(id, null, newPassword);
    }
    
    
    /**
     * 管理后台修改用户
     *
     * @param appUser
     */
    @LogAnnotation(module = "修改用户")
    @PreAuthorize("hasAuthority('back:user:update')")
    @PutMapping("/users")
    public void updateAppUser(@RequestBody UserDTO userDTO) {
    	userService.updateUser(userDTO);
    }
    
    /**
     * 管理后台给用户分配角色
     *
     * @param id      用户id
     * @param roleIds 角色ids
     */
    @LogAnnotation(module = "分配角色")
    @PreAuthorize("hasAuthority('back:user:role:set')")
    @PostMapping("/users/{id}/roles")
    public void setRoleToUser(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
    	userService.setRoleToUser(id, roleIds);
    }
    
    /**
     * 获取用户的角色
     *
     * @param id 用户id
     */
    @PreAuthorize("hasAnyAuthority('back:user:role:set','user:role:byuid')")
    @GetMapping("/users/{id}/roles")
    public Set<RoleDTO> findRolesByUserId(@PathVariable Long id) {
        return userService.findRolesByUserId(id);
    }
	
}
