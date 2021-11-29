
package com.risk.riskmanage.system.mapper;

import java.util.List;

import com.risk.riskmanage.common.mapper.BaseMapper;
import com.risk.riskmanage.system.model.Menu;

/**
 * ClassName:MenuMapper <br/>
 * Description: 菜单mapper. <br/>
 * @see 	 
 */
public interface MenuMapper extends BaseMapper<Menu> {
	/**
	 * isExist:(根据相应的条件判断是否存在重复值). <br/>
	 * @author wz
	 * @param menu 菜单实体类
	 * @return 返回行数
	 */
	Integer isExist(Menu menu);
	
	/**
	 * deleteRole:(根据菜单ids删除菜单信息). <br/>
	 * @author wz
	 * @param deletIds 菜单ids
	 */
	void deleteMenu(Long []deletIds);
	
	/**
	 * selectByRole:(根据角色查询). <br/>
	 * @author wz
	 * @param menu 
	 * @return 查询的菜单类
	 */
	List<Menu> selectByRole(Menu menu);
	
	/**
	 * deleteMenuRole:(删除菜单角色关联表). <br/>
	 * @author wz
	 * @param menu 菜单实体类
	 */
	void deleteMenuRole(Menu menu); 
	
	/**
	 * insertMenuRole:(添加菜单角色关联表). <br/>
	 * @author wz
	 * @param menu 菜单实体类
	 */
	void insertMenuRole(Menu menu); 
	
	/**
	 * findUserMenuByUser:(根据登录用户名查询相应授权菜单列表). <br/>
	 * @author wz
	 * @param loginName 
	 * @return 根据登录用户名查询相应授权菜单列表
	 */
	List<Menu> findUserMenuByUser(String loginName);
}

