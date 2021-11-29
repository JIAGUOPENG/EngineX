
package com.risk.riskmanage.system.service;

import com.risk.riskmanage.system.model.User;

import java.util.List;
import java.util.Set;

/**
 * ClassName:UserService <br/>
 * Description: 后台用户管理service接口. <br/>
 */
public interface UserService {
	
	/**
	 * getUserList:(返回后台用户列表). <br/>
	 * @author wz
	 * @param user 
	 * @return 用户列表
	 */	
	List<User> getUserList(User user);
	
	/**
	 * getUserCount:(返回后台用户行数). <br/>
	 * @author wz
	 * @param user 
	 * @return 返回后台用户行数
	 */	
	int getUserCount(User user);
	
	/**
	 * insertUser:(新增后台用户记录). <br/>
	 * @author wz
	 * @param user 
	 */	
	void insertUser(User user);
	
	/**
	 * updateUser:(修改后台用户记录). <br/>
	 * @author wz
	 * @param user 
	 */	
	void updateUser(User user);	
	
	/**
	 * isExistByLoginName:(判断是否存在相同后台用户名称). <br/>
	 * @author wz
	 * @param loginName 后台用户登录名称
	 * @param userId 后台用户主键id
	 * @return true为存在相同的后台用户登录名称
	 */
	boolean isExistByLoginName(String loginName,Long userId);
	
	/**
	 * isExistUser:(是否存在用户). <br/>
	 * @author wz
	 * @param loginName 用户名
	 * @param password 密码
	 * @return User 返回存在用户或者null
	 */
	User selectLoginInfo(String loginName,String password);
	
	/**
	 * deleteUser:(根据后台用户ids删除后台用户信息). <br/>
	 * @author wz
	 * @param deletIds 后台用户ids
	 * @param status 状态
	 */
	void deleteUser(Long []deletIds,Integer status);
	
	/**
	 * findUserMenuSet:(根据用户名获取所授权的菜单). <br/>
	 * @author wz
	 * @param loginName 
	 * @return 根据用户名获取所授权的菜单
	 */
	Set<String> findUserMenuSet(String loginName);

}

