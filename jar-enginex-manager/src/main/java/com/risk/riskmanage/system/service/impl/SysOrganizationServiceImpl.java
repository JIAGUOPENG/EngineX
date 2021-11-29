
package com.risk.riskmanage.system.service.impl;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.system.model.SysOrganization;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.system.service.SysOrganizationService;
import com.risk.riskmanage.util.SessionManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SysOrganizationServiceImpl extends BaseService implements SysOrganizationService {
	/**
	 * 查询公司
	 */
	@Override
	public List<SysOrganization> getAllSysOrganization() {
		return sysOrganizationMapper.getAllSysOrganization();
	}
	/**
	 * 查询单个公司
	 */
	@Override
	public SysOrganization findById(long id) {
		return sysOrganizationMapper.findById(id);
	}
	/**
	 * 创建公司
	 */
	@Override
	public int createSysOrganization(SysOrganization SysOrganization) {
		//获取登录人信息（超级管理员）
		User user = SessionManager.getLoginAccount();
		String nickName = user.getNickName();
		SysOrganization.setAuthor(nickName);
		//生成token唯一标识
		String uuid = UUID.randomUUID().toString();
		SysOrganization.setToken(uuid);
		return sysOrganizationMapper.createSysOrganization(SysOrganization);
	}
	/**
	 * 修改公司
	 */
	@Override
	public int updateSysOrganization(SysOrganization SysOrganization) {
		return sysOrganizationMapper.updateSysOrganization(SysOrganization);
	}
	/**
	 * 删除公司
	 */
	@Override
	public int deleteSysOrganization(long id) {
		//删除对应的角色
		sysRoleMapper.deleteAllRoles(id);
		//删除对应的用户
		sysUserMapper.deleteAllUser(id);
		//删除用户角色关系
		sysUserMapper.deleteAllUserRole(id);
		//删除公司
		int num = sysOrganizationMapper.deleteSysOrganization(id);
		return num;
	}
	/**
	 * 批量修改公司状态
	 */
	@Override
	public int updateStatus(int status, List<Integer> list) {
		//删除、停用、启用每个公司下的所有角色
		sysRoleMapper.deleteRolesByOrgans(status,list);
		//删除、停用、启用每个公司下的所有账号
		sysUserMapper.deleteUsersByOrgans(status,list);
		//删除、停用、启用每个公司下的所有用户角色关系
		sysUserMapper.deleteUserRoleByOrgan(status,list);
		//删除、停用、启用公司
		int num = sysOrganizationMapper.updateStatus(status, list);
		return num;
	}
	/**
	 * 获取所有启用公司
	 */
	@Override
	public List<SysOrganization> getAllValidOrgan() {
		return sysOrganizationMapper.getAllValidOrgan();
	}
	/**
	 * 验证唯一性
	 */
	@Override
	public List<SysOrganization> validateOrganOnly(
			SysOrganization SysOrganization) {
		return sysOrganizationMapper.validateOrganOnly(SysOrganization);
	}

}