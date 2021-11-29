
package com.risk.riskmanage.system.service.impl;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.system.model.SysMenu;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.system.service.SysMenuService;
import com.risk.riskmanage.util.SessionManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl extends BaseService implements SysMenuService {
	/**
	 * 查询所有资源
	 */
	@Override
	public List<SysMenu> getAllSysMenu() {
		return sysMenuMapper.getAllSysMenu();
	}
	/**
	 * 查询单条资源
	 */
	@Override
	public SysMenu findById(long id) {
		return sysMenuMapper.findById(id);
	}
	/**
	 * 新增资源
	 */
	@Override
	public int createSysMenu(SysMenu sysMenu) {
		//获取登录人信息
		User user = SessionManager.getLoginAccount();
		//分配者
    	long userId = user.getUserId();
    	sysMenu.setUserId(userId);
		return sysMenuMapper.createSysMenu(sysMenu);
	}
	/**
	 * 修改资源
	 */
	@Override
	public int updateSysMenu(SysMenu sysMenu) {
		return sysMenuMapper.updateSysMenu(sysMenu);
	}
	/**
	 * 删除资源
	 */
	@Override
	public int deleteSysMenu(long id) {
		return sysMenuMapper.deleteSysMenu(id);
	}
	/**
	 * 修改资源状态
	 */
	@Override
	public int updateStatus(int status, List<Integer> list) {
		return sysMenuMapper.updateStatus(status, list);
	}
	/**
	 * 通过父节点查询子菜单
	 */
	@Override
	public List<SysMenu> findChildByParent(long parentId) {
		return sysMenuMapper.findChildByParent(parentId);
	}
	/**
	 * 通过角色菜单关系表及父节点查询菜单
	 */
	@Override
	public List<SysMenu> findRoleMenuByParent(long parentId, long roleId) {
		return sysMenuMapper.findRoleMenuByParent(parentId, roleId);
	}
	
	/**
	 * 获取所有启用资源
	 */
	@Override
	public List<SysMenu> getAllValidMenu() {
		return sysMenuMapper.getAllValidMenu();
	}
	
	/**
	 * 分配资源树
	 */
	@Override
	public List<SysMenu> findTreeList(long roleId) {
		return sysMenuMapper.findTreeList(roleId);
	}
	/**
	 * 保存、修改资源树（实现修改）
	 */
	@Override
	public int insertRoleMenu(long roleId, List<Integer> list) {
		//先删除原关系
		sysMenuMapper.deleteRoleMenu(roleId);
		//保存菜单角色关系
		int insertNum = sysMenuMapper.insertRoleMenu(roleId, list);
		return insertNum;
	}

	/**
	 * 获取引擎资源树
	 */
	@Override
	public List<Map<String, String>> getEngineTree(long roleId) {
		return sysMenuMapper.getEngineTree(roleId) ;
	}
	/**
	 * 保存引擎树
	 */
	@Override
	public int insertRoleEngine(long roleId, List<String> list) {
		//先删除原关系
	    sysMenuMapper.deleteRoleEngine(roleId);
		//保存菜单角色关系
		int insertNum = sysMenuMapper.insertRoleEngine(roleId, list);
		return insertNum;
	}
	/**
	 * 删除引擎树
	 */
	@Override
	public int deleteRoleEngine(long roleId) {
		return sysMenuMapper.deleteRoleEngine(roleId);
	}
	/**
	 * 删除资源树
	 */
	@Override
	public int deleteRoleMenu(long roleId) {
		return sysMenuMapper.deleteRoleMenu(roleId);
	}
	/**
	 * 验证唯一性
	 */
	@Override
	public List<SysMenu> validateMenuOnly(SysMenu sysMenu) {
		return sysMenuMapper.validateMenuOnly(sysMenu);
	}
	/**
	 * 验证是否有查看资源的权限
	 */
	@Override
	public List<SysMenu> validatePermission(long roleId, String url) {
		return sysMenuMapper.validatePermission(roleId, url);
	}
	/**
	 * 验证是否有访问该引擎的权限
	 */
	@Override
	public List<Map<String, String>> validateEnginePermission(long roleId,
			String id_e) {
		return sysMenuMapper.validateEnginePermission(roleId, id_e);
	}

}