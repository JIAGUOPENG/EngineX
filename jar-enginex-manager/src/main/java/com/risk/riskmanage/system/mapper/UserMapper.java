package com.risk.riskmanage.system.mapper;

import com.risk.riskmanage.common.mapper.BaseMapper;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.system.model.UserRole;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: UserMapper <br/>
 * @Description: 后台用户管理mapper接口. <br/>
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * isExist:(根据相应的条件判断是否存在重复值). <br/>
     *
     * @param user 用户实体类
     * @return 返回行数
     * @author wz
     */
    Integer isExist(User user);

    /**
     * selectLoginInfo:(用户登录判断). <br/>
     *
     * @param user 用户实体类
     * @return User
     * @author wz
     */
    User selectLoginInfo(User user);

    /**
     * deleteDept:(根据用户ids删除用户信息). <br/>
     *
     * @param user 用户id
     * @author wz
     */
    void deleteUser(User user);

    /**
     * insertUserRole:(增加记录到用户角色关系表). <br/>
     *
     * @param userRolelist 用户角色关系list
     * @author wz
     */
    void insertUserRole(List<UserRole> userRolelist);

    /**
     * deleteUserRole:(根据用户ids删除用户角色关联表信息). <br/>
     *
     * @param deletIds 用户ids
     * @author wz
     */
    void deleteUserRole(Long[] deletIds);

    /**
     * findUserMenuSet:(根据用户名获取所授权的菜单). <br/>
     *
     * @param loginName
     * @return 根据用户名获取所授权的菜单
     * @author wz
     */
    Set<String> findUserMenuSet(String loginName);

    User findUserById(Integer userId);

    String findNickNameById(Long id);

}
