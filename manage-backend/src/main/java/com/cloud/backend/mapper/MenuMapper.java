package com.cloud.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.cloud.backend.entity.Menu;
import com.cloud.backend.provider.MenuProvider;

@Mapper
public interface MenuMapper {
	
	@Insert("insert into sys_menu(parent_id, name, url, css, sort, create_time, update_time) "
			+ "values (#{parentId}, #{name}, #{url}, #{css}, #{sort}, #{createTime}, #{updateTime})")
	public void save(Menu menu);
	
	public void update(Menu menu);
	
	@Delete("delete from sys_menu where id = #{id}")
	public void delete(Long id);
	
	@Select("select * from sys_menu t order by t.sort")
	public List<Menu> findAllMenus();

	@Select("select * from sys_menu t where t.id = #{id}")
	public Menu findMenuById(Long id);
	
	@Delete("delete from sys_menu where parent_id = #{id}")
	public int deleteByParentId(Long id);
}
