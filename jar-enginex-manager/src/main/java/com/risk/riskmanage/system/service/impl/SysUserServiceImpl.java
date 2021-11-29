
package com.risk.riskmanage.system.service.impl;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.system.model.SysUser;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.system.service.SysUserService;
import com.risk.riskmanage.util.MD5;
import com.risk.riskmanage.util.SessionManager;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class SysUserServiceImpl extends BaseService implements SysUserService {
	/**
	 * 查询搜索用户
	 */
	@Override
	public List<SysUser> getAllUsers(SysUser sysUser) {
		return sysUserMapper.getAllUsers(sysUser);
	}
	/**
	 * 查询本组织单个用户
	 * @return
	 */
	@Override
	public SysUser findById(SysUser sysUser) {
		return sysUserMapper.findById(sysUser);
	}
	/**
	 * 创建用户
	 */
	@Override
	public long createSysUser(SysUser sysUser) {
		long num = 0;
		//密码加密
		String password = MD5.GetMD5Code("111111");
		sysUser.setPassword(password);
    	//创建人
		User user = SessionManager.getLoginAccount();
    	String nickName = user.getNickName();
    	sysUser.setAuthor(nickName);
    	//创建用户并返回id
    	sysUserMapper.createSysUser(sysUser);
    	long  uId = sysUser.getId();
    	long roleId = sysUser.getSysRole().getId();
    	long orgaId = sysUser.getOrganId();
    	if(uId!=0&&roleId!=0){
    		//添加表关系
    		num = sysUserMapper.insertUserRole(uId, roleId,orgaId);
    	}
		return num;
	}
	/**
	 * 修改本公司用户
	 */
	@Override
	public int updateSysUser(SysUser sysUser) {
		int num = 0;
		//修改用户
		int updateNum = sysUserMapper.updateSysUser(sysUser);
		//修改用户角色关系
		if(updateNum==1){
			 num = sysUserMapper.updateUserRole(sysUser);
		}
		return num;
	}
	/**
	 * 删除本公司用户
	 */
	@Override
	public int deleteSysUser(long id) {
		//删除单个用户
		int num = sysUserMapper.deleteSysUser(id);
		return num;
	}
	/**
	 * 修改用户状态
	 */
	@Override
	public int updateStates(int status,List<Integer> list) {
		return sysUserMapper.updateStates(status,list);
	}
	/**
	 * 通过用户id查询角色
	 */
	@Override
	public SysUser findRoleByUserId(long userId) {
		return sysUserMapper.findRoleByUserId(userId);
	}
	/**
	 * 重置密码
	 */
	@Override
	public int resetPassword(SysUser sysUser) {
		//密码加密
		String password = MD5.GetMD5Code("111111");
		sysUser.setPassword(password);
		return sysUserMapper.resetPassword(sysUser);
	}
	/**
	 * 修改密码
	 */
	@Override
	public int updatePassword(SysUser sysUser) {
		//获取登录人id
//		User user = SessionManager.getLoginAccount();
//    	long id = user.getUserId();
		//密码加密
		String password = MD5.GetMD5Code(sysUser.getPassword());
		sysUser.setPassword(password);
//		sysUser.setId(id);
		return sysUserMapper.updatePassword(sysUser);
	}
	/**
	 * 本公司账号员工编号唯一性
	 */
	@Override
	public List<SysUser> validateUserOnly(SysUser sysUser) {
		return sysUserMapper.validateUserOnly(sysUser);
	}
	/**
	 * 删除本公司所有账号
	 */
	@Override
	public int deleteAllUser(long organId) {
		return sysUserMapper.deleteAllUser(organId);
	}
	/**
	 * 删除本公司所有账号角色关系
	 */
	@Override
	public int deleteAllUserRole(long organId) {
		return sysUserMapper.deleteAllUserRole(organId);
	}
	
	/**
	 * 删除多个公司的所有用户
	 */
	@Override
	public int deleteUsersByOrgans(Integer status, List<Integer> list) {
		return sysUserMapper.deleteUsersByOrgans(status, list);
	}
	/**
	 * 删除多个公司的用户角色关系
	 */
	@Override
	public int deleteUserRoleByOrgan(Integer status, List<Integer> list) {
		return sysUserMapper.deleteUserRoleByOrgan(status, list);
	}
	/**
	 * 删除角色账号关联关系
	 */
	@Override
	public int deleteUserRoleById(long RoleId) {
		return sysUserMapper.deleteUserRoleById(RoleId);
	}
	/**
	 * 查询角色下的所有账号
	 */
	@Override
	public List<Long> getUserIdsByRoleId(long roleId) {
		return sysUserMapper.getUserIdsByRoleId(roleId);
	}
	/**
	 * 删除角色关联的账号
	 */
	@Override
	public int deleteUsersByIds(Integer status, List<Long> list) {
		int result = 0;
		if (list!=null&&list.size()>0){
			result = sysUserMapper.deleteUsersByIds(status, list);
		}
		return result;
	}
	/**
	 * 批量删除角色账号关系
	 */
	@Override
	public int deleteBatchUserRole(Integer status, List<Integer> list) {
		return sysUserMapper.deleteBatchUserRole(status, list);
	}
	/**
	 * 批量查询角色关联的账号
	 */
	@Override
	public List<Long> getBatchUserIdsByRoleId(List<Integer> list) {
		return sysUserMapper.getBatchUserIdsByRoleId(list);
	}
}