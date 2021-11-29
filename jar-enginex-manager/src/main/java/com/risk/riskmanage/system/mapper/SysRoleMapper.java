
package com.risk.riskmanage.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.risk.riskmanage.system.model.SysRole;



public interface SysRoleMapper {
    /**
     * 获取本组织所有角色
     * @return
     */
    public List<SysRole> getAllSysRole(long organId);
    /**
     * 获取所有角色
     */
    public List<SysRole> getAllRoles();
    /**
     * 获取本组织启用的角色
     * @param organId
     * @return
     */
    public List<SysRole> getAllValidRole(@Param("organId")long organId,@Param("author")String author);
    /**
     * 查询本组织的单个角色
     * @param id
     * @return
     */
    public SysRole findById(@Param("id") long id, @Param("organId") long organId);
    /**
     * 查询单个角色
     * @param id
     * @return
     */
    public SysRole findByAId(long id);
    
    /**
     * 创建本组织角色
     * @param sysRole
     * @return
     */
    public int createSysRole(SysRole sysRole);
    /**
     * 修改本公司角色
     * @param sysRole
     * @return
     */
    public int updateSysRole(SysRole sysRole);
    /**
     * 删除本公司角色
     * @param id
     * @return
     */
    public int deleteSysRole(long id);
    /**
     * 创建公司管理员角色
     */
    public int createOrganRole(SysRole sysRole);
    /**
     * 修改角色状态(停用、启用、删除)
     * @param id
     * @param idList
     * @return
     */
    public int updateStatus(@Param("status") int status, @Param("list") List<Integer> list);
    /**
     * 根据角色查询角色所在公司
     */
    public long getOrganByRoleId(long roleId);
    
    /**
     * 验证角色唯一性
     */
    public List<SysRole> validateRoleOnly(SysRole sysRole);
    /**
     * 查询公司管理员角色id
     */
    public List<SysRole> getOrganRoleByAuthor(SysRole sysRole);
    /**
     * 删除本公司所有角色
     */
    public int deleteAllRoles(long organId);
    /**
     * 删除多个公司的所有角色
     */
    public int deleteRolesByOrgans(@Param("status") Integer status,@Param("list")List<Integer> list);
}
