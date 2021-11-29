
package com.risk.riskmanage.system.service;

import java.util.List;

import com.risk.riskmanage.system.model.Role;

/**
 * ClassName:RoleService <br/>
 * Description: 角色管理service接口. <br/>
 */
public interface RoleService {

	/**
	 * getRoleList:(根据相应条件查询角色信息列表). <br/>
	 * @author wz
	 * @param role 
	 * @return 角色信息列表
	 */
	List<Role> getRoleList(Role role);
	
	/**
	 * getRoleCount:(根据相应条件返回记录行数). <br/>
	 * @author wz
	 * @param role 
	 * @return 返回角色记录行数
	 */	
	int getRoleCount(Role role);
	
	/**
	 * insertRole:(新增角色记录). <br/>
	 * @author wz
	 * @param role 
	 */	
	void insertRole(Role role);
	
	/**
	 * updateRole:(新增角色记录). <br/>
	 * @author wz
	 * @param role 
	 */	
	void updateRole(Role role);	
	
	/**
	 * isExistByRoleName:(判断是否存在相同角色名称). <br/>
	 * @author wz
	 * @param roleName 角色名称
	 * @param roleId 角色主键id
	 * @return true为存在相同的角色名称
	 */
	boolean isExistByRoleName(String roleName,Long roleId);
	
	/**
	 * isExistByRoleCode:(判断是否存在相同角色编号). <br/>
	 * @author wz
	 * @param roleCode 角色编号
	 * @param roleId 角色主键id
	 * @return true为存在相同的角色编号
	 */
	boolean isExistByRoleCode(String roleCode,Long roleId);
	
	/**
	 * deleteRole:(根据角色ids删除角色信息). <br/>
	 * @author wz
	 * @param deletIds 角色ids
	 */
	void deleteRole(Long []deletIds );
}

