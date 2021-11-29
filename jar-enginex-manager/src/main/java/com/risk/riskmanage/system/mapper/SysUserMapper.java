
package com.risk.riskmanage.system.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.risk.riskmanage.system.model.SysUser;





public interface SysUserMapper {
    /**
     * 查询搜索用户
     */
    public List<SysUser> getAllUsers(SysUser sysUser);
    /**
     * 查询本组织单个用户
     * @param
     * @return
     */
    public SysUser findById(SysUser sysUser);
    /**
     * 创建本公司用户 
     * @param sysUser
     * @return
     */
    public long createSysUser(SysUser sysUser);
    /**
     * 添加用户角色关系
     * @param userId
     * @param roleId
     * @return
     */
    public int insertUserRole(@Param("userId") long userId,
    						  @Param("roleId") long roleId,
    						  @Param("organId") long organId);
    /**
     * 修改本公司用户
     * @param sysUser
     * @return
     */
    public int updateSysUser(SysUser sysUser);
    /**
     * 修改用户角色关系
     * @param sysUser
     * @return
     */
    public int updateUserRole(SysUser sysUser);
    /**
     * 删除本公司用户
     * @param id
     * @return
     */
    public int deleteSysUser(long id);
    /**
     * 修改用户状态(停用/启用/删除)
     * @param states
     * @return
     */
    public int updateStates(@Param ("status") int status,@Param ("list") List<Integer> list);
    /**
     * 通过用户id查询角色
     * @param userId
     * @return
     */
    public SysUser findRoleByUserId(long userId);
    
    /**
     * 重置密码
     */
    public int resetPassword(SysUser sysUser);
    
    /**
     * 修改密码
     */
    public int updatePassword(SysUser sysUser);
    
    /**
     * 本公司账号员工编号唯一性
     */
    public List<SysUser> validateUserOnly(SysUser sysUser);
    
    /**
     * 删除本公司的所有账号
     */
    public int deleteAllUser(long organId);
    /**
     * 删除本公司的用户角色关系
     */
    public int deleteAllUserRole(long organId);
    /**
     * 删除多个公司的所有账号
     */
    public int deleteUsersByOrgans(@Param("status")Integer status,@Param("list")List<Integer> list);
    /**
     * 删除多个公司的用户角色关系
     */
    public int deleteUserRoleByOrgan(@Param("status")Integer status,@Param("list")List<Integer> list);
    /**
     * 删除角色账号关联关系
     */
    public int deleteUserRoleById(long RoleId);
    /**
     * 查询本角色下的所有账号
     */
    public List<Long> getUserIdsByRoleId(long roleId);
    /**
     * 删除角色关联的所有账号
     */
    public int deleteUsersByIds(@Param("status") Integer status,@Param("list")List<Long> list);
    
    /**
     * 批量删除角色账号关系
     */
    public int deleteBatchUserRole(@Param("status") Integer status,@Param("list")List<Integer> list);
    /**
     * 批量查询角色关联的账号
     */
    public List<Long> getBatchUserIdsByRoleId(List<Integer> list);
}
