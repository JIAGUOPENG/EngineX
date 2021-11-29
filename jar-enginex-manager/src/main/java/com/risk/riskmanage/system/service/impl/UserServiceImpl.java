
package com.risk.riskmanage.system.service.impl;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.system.model.UserRole;
import com.risk.riskmanage.system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * ClassName:UserServiceImpl <br/>
 * Description: 后台用户管理service接口实现类. <br/>
 */
@Service
public class UserServiceImpl extends BaseService  implements UserService {
	
	@Override
	public List<User> getUserList(User user) {
		return userMapper.selectByExample(user);
	}

	@Override
	public int getUserCount(User user) {
		return userMapper.countByExample(user);
	}

	@Override
	public void insertUser(User user) {
		List<UserRole> userRolelist = new ArrayList<UserRole>();
		String roleCodes = user.getUserId().toString();
		if(roleCodes != null || !("".equals(roleCodes)) ){
			List<String> roleCodeList = Arrays.asList(roleCodes.split(","));
			userMapper.insertSelective(user);
		    for (int i = 0; i < roleCodeList.size(); i++) {
		    	UserRole userRole = new UserRole();
		    	userRole.setUserId(user.getUserId());
		    	userRole.setRoleCode(roleCodeList.get(i));
		    	userRolelist.add(userRole);
			}
			userMapper.insertUserRole(userRolelist);	
		}
	}

	@Override
	public void updateUser(User user) {
		List<UserRole> userRolelist = new ArrayList<UserRole>();
		String roleCodes =user.getUserId().toString();
		if(roleCodes != null && !("".equals(roleCodes)) ){
			List<String> roleCodeList = Arrays.asList(roleCodes.split(","));
		    for (int i = 0; i < roleCodeList.size(); i++) {
		    	UserRole userRole = new UserRole();
		    	userRole.setUserId(user.getUserId());
		    	userRole.setRoleCode(roleCodeList.get(i));
		    	userRolelist.add(userRole);
			}
		    userMapper.deleteUserRole(new Long[]{user.getUserId()});
			
			userMapper.insertUserRole(userRolelist);
			userMapper.updateByPrimaryKeySelective(user);
		}else{
			userMapper.updateByPrimaryKeySelective(user);
		}

	}

	@Override
	public boolean isExistByLoginName(String loginName, Long userId) {
		 if(loginName != null && !("".equals(loginName))){
			 User user = new User();
			 user.setAccount(loginName);
			 user.setUserId(userId);
			 int rowCount=userMapper.isExist(user);
			 if(rowCount>0){
				 return true;
			 }
		 }
		return false;
	}
	
	@Override
	public User selectLoginInfo(String loginName,String password) {
		 if(loginName != null && !("".equals(loginName))){
			 User user = new User();
			 user.setAccount(loginName);
			 user.setPassword(password);
			 User userInfo=userMapper.selectLoginInfo(user);
			 if(userInfo != null){
				 return userInfo;
			 }
		 }
		return null;
	}

	@Override
	public void deleteUser(Long[] deletIds,Integer status) {
		User user = new User();
		user.setDeletIds(deletIds);
		if(status == -1){
			user.setStatus(-1);
			userMapper.deleteUserRole(deletIds);
		}else if(status == 1){
			user.setStatus(1);
		}
		userMapper.deleteUser(user);
	}

	@Override
	public Set<String> findUserMenuSet(String loginName) {
		return userMapper.findUserMenuSet(loginName);
	}

}

