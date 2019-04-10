package com.cloud.backend.provider;

import org.apache.commons.lang3.StringUtils;

import com.cloud.backend.entity.Menu;

public class MenuProvider {
	
	public String update(Menu menu) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("update sys_menu m set ");
		if(null != menu.getParentId()) {
			stringBuffer.append("m.parent_id = #{menu.parentId}, ");
		}
		if(StringUtils.isNotBlank(menu.getName())) {
			stringBuffer.append("m.name = #{menu.name}, ");
		}
		if(StringUtils.isNotBlank(menu.getCss())) {
			stringBuffer.append("m.css = #{menu.css}, ");
		}
		if(StringUtils.isNotBlank(menu.getUrl())) {
			stringBuffer.append("m.url = #{menu.url}, ");
		}
		if(null != menu.getSort()) {
			stringBuffer.append("m.sort = #{menu.sort}, ");
		}
		if(null != menu.getCreateTime()) {
			stringBuffer.append("m.create_time = #{menu.createTime}, ");
		}
		if(null != menu.getUpdateTime()) {
			stringBuffer.append("m.update_time = #{menu.updateTime}, ");
		}
		
		stringBuffer.deleteCharAt(stringBuffer.length()-1);
		
		stringBuffer.append("where m.id = #{menu.id}");
		return stringBuffer.toString();
	}

}
