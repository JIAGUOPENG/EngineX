
package com.risk.riskmanage.system.service;

import com.risk.riskmanage.system.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysMenuService {
    /**
     * 查询所有资源 
     * @return
     */
	public List<SysMenu> getAllSysMenu();
    /**
     * 查询单条资源
     * @param id
     * @return
     */
    public SysMenu findById(long id);
    /**
     * 新增资源
     * @param sysMenu
     * @return
     */
    public int createSysMenu(SysMenu sysMenu);
    /**
     * 修改资源
     * @param sysMenu
     * @return
     */
    public int updateSysMenu(SysMenu sysMenu);
    /**
     * 删除资源
     * @param id
     * @return
     */
    public int deleteSysMenu(long id);
    
    /**
     * 修改资源状态
     * @param id
     * @param idList
     * @return
     */
    public int updateStatus(@Param("status") int status, @Param("list") List<Integer> list);
    
    /**
     * 通过父节点查询子菜单
     * @param id
     * @return
     */
    public List<SysMenu> findChildByParent(long parentId);
    
    /**
     * 通过角色菜单关系表及父节点查询菜单
     */
    public List<SysMenu> findRoleMenuByParent(@Param ("parentId")long parentId, @Param("roleId") long roleId);
    
    /**
     * 保存角色菜单关系（实现修改）
     */
    public int insertRoleMenu(@Param("roleId") long roleId ,@Param("list") List<Integer> list);
    /**
     * 分配资源树
     * @param roleId
     * @return
     */
    public List<SysMenu> findTreeList(long roleId);
    /**
     * 获取所有启用资源
     * @return
     */
    public List<SysMenu> getAllValidMenu();
    
    /**
     * 获取引擎资源树
     */
    public List<Map<String,String>> getEngineTree(long roleId);
    
    /**
     * 保存引擎树
     */
    public int insertRoleEngine(@Param ("roleId") long roleId,@Param ("list") List<String> list);
    /**
     * 删除引擎树（实现修改）
     */
    public int deleteRoleEngine(long roleId);
    
    /**
     * 删除角色菜单关系(实现修改)
     */
    public int deleteRoleMenu(long roleId);
    /**
     * 验证唯一性
     */
    public List<SysMenu> validateMenuOnly(SysMenu sysMenu);
    
    /**
     * 验证是否有查看菜单的权限
     */
    public List<SysMenu> validatePermission(@Param("roleId")long roleId,@Param("url")String url);
    /**
     * 验证是否有该引擎的权限
     */
    public List<Map<String,String>> validateEnginePermission(@Param("roleId")long roleId,@Param("id_e") String id_e);
}
