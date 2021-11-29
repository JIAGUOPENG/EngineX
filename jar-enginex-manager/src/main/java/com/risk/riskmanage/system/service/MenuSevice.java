
package com.risk.riskmanage.system.service;

import java.util.List;

import com.risk.riskmanage.system.model.Menu;
import com.risk.riskmanage.system.model.MenuJson;


/**
 * ClassName:MenuSevice <br/>
 * Description: 菜单service. <br/>
 */
public interface MenuSevice {
	/**
	 * getMenuList:(根据相应条件查询菜单信息列表). <br/>
	 * @author wz
	 * @param menuVo  
	 * @return 菜单信息列表
	 */
	List<Menu> getMenuList(Menu menu);
	
	/**
	 * getMenuCount:(根据相应条件返回记录行数). <br/>
	 * @author wz
	 * @param menuVo 
	 * @return 返回菜单记录行数
	 */	
	int getMenuCount(Menu menu);
	
	/**
	 * insertMenu:(新增菜单记录). <br/>
	 * @author wz
	 * @param menuVo 
	 */	
	void insertMenu(Menu menu);
	
	/**
	 * updateMenu:(新增菜单记录). <br/>
	 * @author wz
	 * @param menuVo 
	 */	
	void updateMenu(Menu menu);	
	
	/**
	 * isExistByMenuName:(判断是否存在相同菜单名称). <br/>
	 * @author wz
	 * @param menuName 菜单名称
	 * @param menuId 菜单主键id
	 * @return true为存在相同的菜单名称
	 */
	boolean isExistByMenuName(String menuName,Long menuId);
	
	/**
	 * isExistByMenuCode:(判断是否存在相同菜单编号). <br/>
	 * @author wz
	 * @param menuCode 菜单编号
	 * @param menuId 菜单主键id
	 * @return true为存在相同的菜单编号
	 */
	boolean isExistByMenuCode(String menuCode,Long menuId);
	
	/**
	 * deleteMenu:(根据菜单ids删除菜单信息). <br/>
	 * @author wz
	 * @param deletIds 菜单ids
	 */
	void deleteMenu(Long []deletIds );
	
	/**
	 * selectByRole:(根据角色查询). <br/>
	 * @author wz
	 * @param menuVo 
	 * @return 查询的菜单类
	 */
	List<Menu> selectByRole(Menu menu);
	
	/**
	 * deleteMenu:(返回true为角色权限修改成功). <br/>
	 * @author wz
	 * @param deletIds 菜单ids
	 * @param roleCode 角色编号
	 * @return 返回true为角色权限修改成功
	 */
	boolean addMenuRole(Long []deletIds,String roleCode);
	
	/**
	 * findUserMenuByUser:(根据登录用户名查询相应授权菜单列表). <br/>
	 * @author wz
	 * @param loginName 
	 * @return 根据登录用户名查询相应授权菜单列表
	 */
	List<MenuJson> findUserMenuByUser(String loginName);
}

