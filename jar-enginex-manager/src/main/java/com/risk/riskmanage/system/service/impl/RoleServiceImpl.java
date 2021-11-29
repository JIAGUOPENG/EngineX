
package com.risk.riskmanage.system.service.impl;

import java.util.List;

import com.risk.riskmanage.system.service.RoleService;
import org.springframework.stereotype.Service;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.system.model.Role;

/**
 * ClassName:RoleServiceImpl <br/>
 * Description: 角色管理service接口实现. <br/>
 */
@Service
public class RoleServiceImpl extends BaseService implements RoleService {

	@Override
	public List<Role> getRoleList(Role role) {
		return roleMapper.selectByExample(role);
	}

	@Override
	public int getRoleCount(Role role) {
		return roleMapper.countByExample(role);
	}

	@Override
	public void insertRole(Role role) {
		roleMapper.insertSelective(role);
	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public boolean isExistByRoleName(String roleName, Long roleId) {
		 if(roleName != null && !("".equals(roleName))){
			 Role role = new Role();
			 role.setId(roleId);
			 role.setName(roleName);
			 int rowCount=roleMapper.isExist(role);
			 if(rowCount>0){
				 return true;
			 }
		 }
		return false;
	}

	@Override
	public boolean isExistByRoleCode(String roleCode, Long roleId) {
		 if(roleCode != null && !("".equals(roleCode))){
			 Role role = new Role();
			 role.setId(roleId);
			 role.setRoleCode(roleCode);
			 int rowCount=roleMapper.isExist(role);
			 if(rowCount>0){
				 return true;
			 }
		 }
		return false;
	}

	@Override
	public void deleteRole(Long[] deletIds) {
		roleMapper.deleteRole(deletIds);
	}

}

