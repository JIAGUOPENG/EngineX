
package com.risk.riskmanage.system.service.impl;


import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.system.model.SysRole;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.system.service.SysRoleService;
import com.risk.riskmanage.util.SessionManager;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysRoleServiceImpl extends BaseService implements SysRoleService {
	/**
	 * 获取本组织所有角色
	 * @return
	 */
	@Override
	public List<SysRole> getAllSysRole(long organId) {
		return sysRoleMapper.getAllSysRole(organId);
	}
	/**
	 * 获取所有角色
	 */
	@Override
	public List<SysRole> getAllRoles() {
		return sysRoleMapper.getAllRoles();
	}
	/**
	 * 获取本组织的单个角色
	 */
	@Override
	public SysRole findById(long id,long organId) {
		return sysRoleMapper.findById(id,organId);
	}
	/**
	 * 查询单个角色
	 */
	@Override
	public SysRole findByAId(long id) {
		return sysRoleMapper.findByAId(id);
	}
	/**
	 * 创建本公司的角色
	 */
	@Override
	public int createSysRole(SysRole sysRole) {
		return sysRoleMapper.createSysRole(sysRole);
	}
	/**
	 * 修改本公司角色
	 */
	@Override
	public int updateSysRole(SysRole sysRole) {
		//获取管理员所在公司
		User user = SessionManager.getLoginAccount();
    	long organId = user.getOrganId();
    	if(organId!=1){
    		//修改本公司角色
    		sysRole.setOrganId(organId);
    	}
		return sysRoleMapper.updateSysRole(sysRole);
	}
	/**
	 * 删除本公司角色
	 */
	@Override
	public int deleteSysRole(long id) {
		//查询本角色对应的账号id
		List<Long> list = sysUserMapper.getUserIdsByRoleId(id);
		//删除本角色下账号
		if (list!=null&&list.size()>0){
			sysUserMapper.deleteUsersByIds(-1,list);
		}
		//删除账号角色关系
		sysUserMapper.deleteUserRoleById(id);
		//删除角色
		int num = sysRoleMapper.deleteSysRole(id);
		return num;
	}
	/**
	 * 创建公司管理员角色
	 */
	@Override
	public int createOrganRole(SysRole sysRole) {
		return sysRoleMapper.createOrganRole(sysRole);
	}
	/**
	 * 修改角色状态(启用、停用、删除)
	 */
	@Override
	public int updateStatus(int status, List<Integer> list) {
			//查询角色下的账号id
			List<Long> listu = sysUserMapper.getBatchUserIdsByRoleId(list);
			//批量删除角色关联账号
			if (listu!=null&&listu.size()>0){
				sysUserMapper.deleteUsersByIds(status,listu);
			}
			//批量删除角色账号关系
			sysUserMapper.deleteBatchUserRole(status,list);
			//批量删除角色
			int num = sysRoleMapper.updateStatus(status, list);
			return num;
	}
	/**
	 * 根据角色查询公司
	 */
	@Override
	public long getOrganByRoleId(long roleId) {
		return sysRoleMapper.getOrganByRoleId(roleId);
	}
	/**
	 * 验证角色唯一性
	 */
	@Override
	public List<SysRole> validateRoleOnly(SysRole sysRole) {
		return sysRoleMapper.validateRoleOnly(sysRole);
	}
	/**
	 * 查询公司管理员角色id
	 */
	@Override
	public List<SysRole> getOrganRoleByAuthor(SysRole sysRole) {
		return sysRoleMapper.getOrganRoleByAuthor(sysRole);
	}
	/**
	 * 获取公司启用角色
	 */
	@Override
	public List<SysRole> getAllValidRole(long organId, String author) {
		return sysRoleMapper.getAllValidRole(organId, author);
	}
	/**
	 * 删除本公司所有角色
	 * 
	 */
	@Override
	public int deleteAllRoles(long organId) {
		return sysRoleMapper.deleteAllRoles(organId);
	}
	/**
	 * 删除多个公司的所有角色
	 */
	@Override
	public int deleteRolesByOrgans(Integer status, List<Integer> list) {
		return sysRoleMapper.deleteRolesByOrgans(status, list);
	}
}