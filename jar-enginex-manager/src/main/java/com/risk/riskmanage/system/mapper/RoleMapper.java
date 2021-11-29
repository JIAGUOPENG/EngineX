
package com.risk.riskmanage.system.mapper;

import com.risk.riskmanage.system.model.Role;
import com.risk.riskmanage.common.mapper.BaseMapper;


/**
 * ClassName:RoleMapper <br/>
 * Description: 角色管理mapper层. <br/>
 * @see 	 
 */
public interface RoleMapper extends BaseMapper<Role>{
	
	/**
	 * isExist:(根据相应的条件判断是否存在重复值). <br/>
	 * @author wz
	 * @param role 角色实体类
	 * @return 返回行数
	 */
	Integer isExist(Role role);
	
	/**
	 * deleteRole:(根据角色ids删除角色信息). <br/>
	 * @author wz
	 * @param deletIds 角色ids
	 */
	void deleteRole(Long []deletIds);
}

