package com.imxss.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.CacheWrite;
import org.coody.framework.context.annotation.DeBug;
import org.coody.framework.core.jdbc.JdbcHandle;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.imxss.web.constant.CacheFinal;
import com.imxss.web.domain.SysMenus;
import com.imxss.web.domain.UserRole;
import com.imxss.web.schema.MenuSchema;


@Service
public class MenuService {
	
	@Resource
	RoleService roleService;

	@Resource
	JdbcHandle jdbcHandle;
	
	
	@CacheWrite(key=CacheFinal.MENUS_LIST,time=600,fields="roleId")
	@DeBug
	public List<MenuSchema> loadMenus(Integer roleId){
		List<SysMenus> menus=loadSourceMenus(roleId);
		return parseMenus(menus);
	}
	@CacheWrite(key=CacheFinal.MENUS_SOURCES,time=600,fields="roleId")
	public List<SysMenus> loadSourceMenus(Integer roleId){
		try {
			UserRole role=roleService.loadRole(roleId);
			Integer[] menuIds=StringUtil.splitByMosaicIntegers(role.getMenus(), ","); 
			List<SysMenus> menus=jdbcHandle.findBean(SysMenus.class,"id",menuIds);
			return menus;
		} catch (Exception e) {
			return null;
		}
	}
	@CacheWrite(key=CacheFinal.MENUS_LIST,time=7200)
	@DeBug
	public List<MenuSchema> loadAllMenus(){
		List<SysMenus> menus=jdbcHandle.findBean(SysMenus.class);
		return parseMenus(menus);
	}
	@CacheWrite(key=CacheFinal.MENUS_LIST,time=7200)
	public List<MenuSchema> parseMenus(List<SysMenus> menus){
		List<MenuSchema> schemas=new ArrayList<MenuSchema>();
		if(StringUtil.isNullOrEmpty(menus)){
			return null;
		}
		for(SysMenus menu:menus){
			try {
				if(menu.getType()!=0){
					continue;
				}
				List<MenuSchema> childSchemas=new ArrayList<MenuSchema>();
				for(SysMenus childMenu:menus){
					try {
						if(childMenu.getType()!=1){
							continue;
						}
						if(childMenu.getUpId().intValue()==menu.getId().intValue()){
							MenuSchema schema=new MenuSchema();
							BeanUtils.copyProperties(childMenu, schema);
							childSchemas.add(schema);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				childSchemas=PropertUtil.doSeq(childSchemas, "seq");
				MenuSchema schema=new MenuSchema();
				BeanUtils.copyProperties(menu, schema);
				schema.setChildMenus(childSchemas);
				schemas.add(schema);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		schemas=PropertUtil.doSeq(schemas, "seq");
		return schemas;
	}
	
	public static void main(String[] args) {
		Integer [] ints=new Integer[47];
		for(int i=1;i<48;i++){
			ints[i-1]=i;
		}
	}
}
